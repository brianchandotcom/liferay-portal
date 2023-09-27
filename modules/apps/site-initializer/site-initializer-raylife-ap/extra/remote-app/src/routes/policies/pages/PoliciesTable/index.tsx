/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayForm, {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import {ClayPaginationWithBasicItems} from '@clayui/pagination';
import ClayPaginationBar from '@clayui/pagination-bar';
import classNames from 'classnames';
import {useEffect, useState} from 'react';

import Header from '../../../../common/components/header';
import Table, {TableRowContentType} from '../../../../common/components/table';
import {
	Parameters,
	deletePolicyByExternalReferenceCode,
	getNotExpiredPolicies,
} from '../../../../common/services';
import {getPicklistByName} from '../../../../common/services/Picklists';
import {getProducts} from '../../../../common/services/Products';
import formatDate from '../../../../common/utils/dateFormatter';
import {redirectTo} from '../../../../common/utils/liferay';
import useDebounce from '../../../../hooks/useDebounce';

type Policy = {
	commission: number;
	endDate: string;
	externalReferenceCode: string;
	policyOwnerName: string;
	policyStatus: {name: string};
	productName: string;
	startDate: string;
	termPremium: number;
};

type TableContent = {[keys: string]: string | boolean};

type TableItemType = {
	centered?: boolean;
	clickable?: boolean;
	clickableSort?: boolean;
	greyColor?: boolean;
	hasSort?: boolean;
	icon?: boolean;
	key: string;
	redColor?: boolean;
	requestLabel: string;
	type?: string;
	value: string;
};

type itemsPolicyFilter = {
	policyStatus: {name: string};
	productName: string;
};

type itemsPolicies = TableContent;

type itemsProducts = TableContent;

type itemsPicklists = TableContent;

type StateSortType = {
	[keys: string]: boolean;
};

enum Order {
	Ascendant = 'asc',
	Descendant = 'desc',
}

const daysToExpirePolicyAlert = 15;

const PoliciesTable = () => {
	const [policies, setPolicies] = useState<TableContent[]>([]);
	const [totalCount, setTotalCount] = useState<number>(0);
	const [pageSize, setPageSize] = useState<number>(5);
	const [totalPages, setTotalPages] = useState<number>(0);
	const [page, setPage] = useState<number>(1);
	const [firstPaginationLabel, setFirstPaginationLabel] = useState<number>(1);
	const [secondPaginationLabel, setSecondPaginationLabel] = useState<number>(
		1
	);
	const [hasRedLine, setHasRedLine] = useState<boolean>(false);

	const [searchInput, setSearchInput] = useState('');

	const [activeFilter, setActiveFilter] = useState(true);
	const [productFilterItems, setProductFilterItems] = useState<string[]>([]);
	const [statusFilterItems, setStatusFilterItems] = useState<string[]>([]);
	const [filterProductCheck, setFilterProductCheck] = useState<string[]>([]);
	const [filterStatusCheck, setFilterStatusCheck] = useState<string[]>([]);
	const [filterCheckedLabel, setFilterCheckedLabel] = useState<string[]>([]);
	const [checkedStateProduct, setCheckedStateProduct] = useState<boolean[]>(
		[]
	);
	const [checkedStateStatus, setCheckedStateStatus] = useState<boolean[]>([]);
	const [sortPolicyByEndDate, setSortPolicyByEndDate] = useState<string>(
		Order.Ascendant
	);

	const [sortState, setSortState] = useState<StateSortType>({
		commission: false,
		externalReferenceCode: false,
		monthlyPremium: false,
		policyOwnerName: false,
		policyPeriod: true,
		policyStatus: false,
		productName: false,
		renewalDue: true,
	});

	const filterSearch = `contains(policyOwnerName,'${searchInput}') or contains(policyOwnerEmail, '${searchInput}') or contains(externalReferenceCode, '${searchInput}')`;

	const filterProduct = !filterStatusCheck.length
		? `productName in (${filterProductCheck})`
		: `policyStatus in (${filterStatusCheck}) and productName in (${filterProductCheck})`;

	const filterStatus = !filterProductCheck.length
		? `policyStatus in (${filterStatusCheck})`
		: `policyStatus in (${filterStatusCheck}) and productName in (${filterProductCheck})`;

	const generateParameters = (filtered?: string) => {
		const parameters: Parameters =
			filtered === undefined
				? {
						page: '0',
						pageSize: '0',
						sort: `endDate:${sortPolicyByEndDate}`,
				  }
				: {
						filter: filtered,
						page: '0',
						pageSize: '0',
						sort: `endDate:${sortPolicyByEndDate}`,
				  };

		return parameters;
	};

	const [parameters, setParameters] = useState<Parameters>(
		generateParameters()
	);

	const parameterDebounce = useDebounce(parameters, 200);

	parameters.pageSize = pageSize.toString();
	parameters.page = page.toString();

	const handleChangeSearch = (event: React.ChangeEvent<HTMLInputElement>) => {
		setSearchInput(event.target.value);
	};

	const conditionalFilters = () => {
		setPage(1);

		if (
			searchInput &&
			!filterStatusCheck.length &&
			!filterProductCheck.length
		) {
			return setParameters(generateParameters(filterSearch));
		}

		if (searchInput && filterStatusCheck.length) {
			return setParameters(
				generateParameters(filterSearch + ' and ' + filterStatus)
			);
		}

		if (searchInput && filterProductCheck.length) {
			return setParameters(
				generateParameters(filterSearch + ' and ' + filterProduct)
			);
		}

		if (
			!searchInput &&
			(filterStatusCheck.length || filterProductCheck.length)
		) {
			if (filterStatusCheck.length) {
				return setParameters(generateParameters(filterStatus));
			}
			if (filterProductCheck.length) {
				return setParameters(generateParameters(filterProduct));
			}
		}
		if (
			!searchInput &&
			!filterStatusCheck.length &&
			!filterProductCheck.length
		) {
			return setParameters(generateParameters());
		}
	};

	const handleClick = () => {
		conditionalFilters();
	};
	const filterClick = () => {
		setActiveFilter(!activeFilter);
	};

	const itemsCreate = (listItem: string[], checkedItem: boolean[] | []) => {
		const itemsFilters = listItem.map(
			(statusName: string, index: number) => {
				const item: Array<{checked: boolean; item: string}> = [];
				item.push({
					checked: checkedItem[index],
					item: statusName,
				});

				return item;
			}
		);

		return itemsFilters;
	};

	const itemProducts = itemsCreate(productFilterItems, checkedStateProduct);
	const itemStatus = itemsCreate(statusFilterItems, checkedStateStatus);

	useEffect(() => {
		setCheckedStateProduct(
			new Array(productFilterItems.length).fill(false)
		);
		setCheckedStateStatus(new Array(statusFilterItems.length).fill(false));
	}, [productFilterItems.length, statusFilterItems.length]);

	useEffect(() => {
		conditionalFilters();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [filterProductCheck, filterStatusCheck, filterCheckedLabel]);

	const handleKeyDown = (event: React.KeyboardEvent<HTMLElement>) => {
		if (event.key === 'Enter') {
			handleClick();
		}
	};
	const checkItemProduct = (productCheck: string) => {
		setFilterCheckedLabel((old: string[]) => [...old, productCheck]);

		if (!filterProductCheck.includes(productCheck)) {
			setFilterProductCheck((old: string[]) => [
				...old,
				`'${productCheck}'`,
			]);
		}

		return filterProductCheck;
	};

	const uncheckItemProduct = (productName: string) => {
		setFilterCheckedLabel(
			filterCheckedLabel.filter((currentProductName: string) => {
				return productName !== currentProductName;
			})
		);

		setFilterProductCheck(
			filterProductCheck.filter((currentProductName: string) => {
				return currentProductName !== `'${productName}'`;
			})
		);
	};

	const handleProductCheck = (checkedIndex: number, productName: string) => {
		const updatedCheckedState = checkedStateProduct.map(
			(checked: boolean, index: number) => {
				if (index === checkedIndex) {
					return !checked;
				}

				return checked;
			}
		);

		setCheckedStateProduct(updatedCheckedState);

		if (!checkedStateProduct[checkedIndex]) {
			return checkItemProduct(productName);
		}

		return uncheckItemProduct(productName);
	};

	const checkItemStatus = (statusName: string) => {
		setFilterCheckedLabel((old: string[]) => [...old, statusName]);
		if (!filterStatusCheck.includes(statusName)) {
			setFilterStatusCheck((old: string[]) => [
				...old,
				`'${statusName.toLowerCase()}'`,
			]);
		}

		return filterStatusCheck;
	};

	const uncheckItemStatus = (statusName: string) => {
		setFilterCheckedLabel(
			filterCheckedLabel.filter((currentStatusName: string) => {
				return statusName !== currentStatusName;
			})
		);

		setFilterStatusCheck(
			filterStatusCheck.filter((currentStatusName: string) => {
				return currentStatusName !== `'${statusName?.toLowerCase()}'`;
			})
		);
	};

	const handleStatusCheck = (checkedIndex: number, statusName: string) => {
		const updatedCheckedState = checkedStateStatus.map(
			(checked: boolean, index: number) => {
				if (index === checkedIndex) {
					return !checked;
				}

				return checked;
			}
		);

		setCheckedStateStatus(updatedCheckedState);

		if (!checkedStateStatus[checkedIndex]) {
			return checkItemStatus(statusName);
		}

		return uncheckItemStatus(statusName);
	};

	const onClickLabel = (currentFilterName: string) => {
		if (statusFilterItems.includes(currentFilterName)) {
			setFilterStatusCheck(
				filterStatusCheck.filter((statusName: string) => {
					return (
						statusName !== `'${currentFilterName?.toLowerCase()}'`
					);
				})
			);
		}
		else {
			setFilterProductCheck(
				filterProductCheck.filter((productName: string) => {
					return productName !== `'${currentFilterName}'`;
				})
			);
		}

		setFilterCheckedLabel(
			filterCheckedLabel.filter((filterNameActived: string) => {
				return filterNameActived !== currentFilterName;
			})
		);

		const updatedCheckedStateProduct = checkedStateProduct.map(
			(checked: boolean, index: number) => {
				const productName = itemProducts[index]?.[0]?.item;

				if (currentFilterName === productName) {
					return !checked;
				}

				return checked;
			}
		);

		setCheckedStateProduct(updatedCheckedStateProduct);

		const updatedCheckedStateStatus = checkedStateStatus.map(
			(checked: boolean, index: number) => {
				const statusName = itemStatus[index]?.[0]?.item;

				if (currentFilterName === statusName) {
					return !checked;
				}

				return checked;
			}
		);

		setCheckedStateStatus(updatedCheckedStateStatus);
	};

	const HEADERS = [
		{
			bold: false,
			centered: true,
			clickableSort: true,
			hasSort: true,
			key: 'renewalDue',
			redColor: hasRedLine,
			requestLabel: 'renewalDue',
			value: 'Renewal Due',
		},
		{
			clickableSort: false,
			key: 'productName',
			requestLabel: 'productName',
			value: 'Product',
		},
		{
			bold: true,
			clickable: true,
			clickableSort: false,
			key: 'externalReferenceCode',
			requestLabel: 'externalReferenceCode',
			type: 'link',
			value: 'Policy Number',
		},
		{
			clickableSort: false,
			greyColor: true,
			key: 'policyOwnerName',
			requestLabel: 'policyOwnerName',
			value: 'Name',
		},
		{
			clickableSort: false,
			greyColor: true,
			key: 'monthlyPremium',
			requestLabel: 'monthlyPremium',
			value: 'Monthly Premium',
		},
		{
			clickableSort: true,
			greyColor: true,
			hasSort: true,
			key: 'policyPeriod',
			requestLabel: 'renewalDue',
			value: 'Policy Period',
		},
		{
			clickableSort: false,
			greyColor: true,
			key: 'commission',
			requestLabel: 'commission',
			value: 'Commission',
		},
		{
			clickableSort: false,
			greyColor: true,
			key: 'policyStatus',
			requestLabel: 'policyStatus',
			value: 'Status',
		},
	];

	const handleDeletePolicy = (externalReferenceCode: string) => {
		deletePolicyByExternalReferenceCode(externalReferenceCode);

		const filteredPolicies = policies.filter(
			(policy) => !(policy.key === externalReferenceCode)
		);

		setPolicies(filteredPolicies);
		setTotalCount(totalCount - 1);
	};

	const handleEditPolicy = (externalReferenceCode: string) => {
		alert(`Edit ${externalReferenceCode} Action`);
	};

	useEffect(() => {
		getNotExpiredPolicies(generateParameters()).then((results) => {
			const allItems: itemsPolicies[] = [];
			results?.data?.items?.forEach(
				({policyStatus: {name}, productName}: itemsPolicyFilter) => {
					allItems.push({
						name,
						productName,
					});
				}
			);
		});

		getProducts().then((results) => {
			const productsResult = results?.data?.items;

			const products = productsResult.map((product: itemsProducts) => {
				return product.name;
			});

			setProductFilterItems(products);
		});

		getPicklistByName('PolicyStatus').then((results) => {
			const policyStatusResult = results?.data?.listTypeEntries;

			const policyStatuses = policyStatusResult.map(
				(policyStatusPicklist: itemsPicklists) => {
					return policyStatusPicklist.name;
				}
			);

			setStatusFilterItems(policyStatuses);
		});

		return;

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		getNotExpiredPolicies(parameterDebounce).then((results) => {
			const policiesList: TableContent[] = [];
			results?.data?.items?.forEach(
				({
					commission,
					endDate,
					externalReferenceCode,
					policyOwnerName,
					policyStatus,
					productName,
					startDate,
					termPremium,
				}: Policy) => {
					const policyEndDate = Date.parse(endDate);

					const currentDate: Date = new Date();

					const differenceOfDays =
						Number(policyEndDate) - Number(currentDate);

					const renewalDue =
						Math.floor(differenceOfDays / (1000 * 60 * 60 * 24)) +
						1;

					setHasRedLine(
						renewalDue >= 0 && renewalDue < daysToExpirePolicyAlert
					);

					const renewalDueDisplayRules = () => {
						if (renewalDue < 0) {
							return 'Expired';
						}
						if (renewalDue === 0) {
							return 'Due Today';
						}

						return renewalDue;
					};

					const policyPeriod = `${formatDate(
						new Date(startDate),
						true
					)} - ${formatDate(new Date(endDate), true)}`;

					const monthlyPremium = termPremium / 12;

					const commissionValue = Number(commission);

					policiesList.push({
						commission: `$${commissionValue.toFixed(2)}`,
						externalReferenceCode,
						isClickable: productName === 'Auto' ? true : false,
						isExpiring: (renewalDue < 0).toString(),
						isRedLine: (
							renewalDue >= 0 &&
							renewalDue < daysToExpirePolicyAlert
						).toString(),
						key: externalReferenceCode,
						monthlyPremium: `$${monthlyPremium.toFixed(2)}`,
						policyOwnerName,
						policyPeriod: `${policyPeriod}`,
						policyStatus: policyStatus?.name,
						productName,
						renewalDue: `${renewalDueDisplayRules()}`,
						renewalDueCalculation: `${
							renewalDue >= 0 ? renewalDue : null
						}`,
					});
				}
			);
			setPolicies(policiesList);

			const totalCount = results?.data?.totalCount;
			setTotalCount(totalCount);

			const totalPages = Math.ceil(totalCount / pageSize);
			setTotalPages(totalPages);

			const firstPaginationLabel = (page - 1) * pageSize + 1;
			setFirstPaginationLabel(firstPaginationLabel);

			const secondPaginationLabel =
				totalCount > page * pageSize ? page * pageSize : totalCount;
			setSecondPaginationLabel(secondPaginationLabel);
		});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [pageSize, page, searchInput, filterCheckedLabel, parameterDebounce]);

	const handleParametersChange = (filter: string) => {
		setSortPolicyByEndDate(filter);
		setParameters((previous) => ({
			...previous,
			sort: `endDate:${filter}`,
		}));
	};

	const setSortRule = () => {
		sortPolicyByEndDate === Order.Descendant
			? handleParametersChange(Order.Ascendant)
			: handleParametersChange(Order.Descendant);
	};

	const title = `Policies (${totalCount})`;

	const handleRedirectToGmail = (email: string) => {
		window.location.href = `mailto:${email}`;
	};

	const handleRedirectToDetailsPages = (
		externalReferenceCode: string,
		entity: string
	) => {
		redirectTo(`${entity}?externalReferenceCode=${externalReferenceCode}`);
	};

	const onClickRules = (
		item: TableItemType,
		rowContent: TableRowContentType
	) => {
		if (item.clickable && item.key === 'email') {
			handleRedirectToGmail(rowContent[item.key] as string);
		}

		if (
			rowContent['productName'] === 'Auto' &&
			item.key === 'externalReferenceCode'
		) {
			handleRedirectToDetailsPages(
				rowContent['externalReferenceCode'] as string,
				'policy-details'
			);
		}
	};

	const setHeader = () => {
		setSortRule();
	};

	return (
		<div className="ray-dashboard-recent-policies">
			<div className="d-flex justify-content-between responsive-search">
				<Header className="mb-5 pt-3 px-4" title={title} />

				<ClayForm.Group className="flex-row mt-3 px-3">
					<ClayInput.Group className="justify-content-between">
						<ClayInput.GroupItem prepend>
							<ClayInput
								onChange={handleChangeSearch}
								onKeyDown={handleKeyDown}
								placeholder="Search for..."
								type="text"
								value={searchInput}
							/>
						</ClayInput.GroupItem>

						<ClayInput.GroupItem append shrink>
							<ClayButton
								displayType="secondary"
								onClick={handleClick}
								type="submit"
							>
								<ClayIcon symbol="search" />
							</ClayButton>
						</ClayInput.GroupItem>

						<ClayButtonWithIcon
							aria-label="Filter"
							displayType="secondary"
							onClick={filterClick}
							symbol="filter"
							type="submit"
						></ClayButtonWithIcon>
					</ClayInput.Group>
				</ClayForm.Group>
			</div>

			<div
				className={classNames('mb-2 mr-3 ', {
					'd-flex': !activeFilter,
					'd-none': activeFilter,
				})}
			>
				<div className="d-flex justify-content-center ml-4 responsive-filter">
					<div className="d-flex">
						<ClayDropDown
							className='"d-flex mr-3"'
							trigger={
								<ClayButton
									className="btn-sm hover-button-dropdown mr-3 shadow-none text-neutral-8"
									displayType="secondary"
								>
									Product
									<ClayIcon symbol="caret-double-l" />
								</ClayButton>
							}
						>
							<ClayDropDown.ItemList className="border-neutral-6 ml-3 mt-2">
								{productFilterItems.map(
									(
										productName: string,
										checkedIndex: number
									) => (
										<ClayCheckbox
											checked={
												checkedStateProduct[
													checkedIndex
												] ?? false
											}
											key={checkedIndex}
											label={
												itemProducts[checkedIndex]?.[0]
													?.item
											}
											onChange={() =>
												handleProductCheck(
													checkedIndex,
													productName
												)
											}
										/>
									)
								)}
							</ClayDropDown.ItemList>
						</ClayDropDown>

						<ClayDropDown
							className="mr-3"
							trigger={
								<ClayButton
									className="btn-sm hover-button-dropdown mr-2 shadow-none text-neutral-8"
									displayType="secondary"
								>
									Status
									<ClayIcon symbol="caret-double-l" />
								</ClayButton>
							}
						>
							<ClayDropDown.ItemList className="border-neutral-6 ml-3 mt-2">
								{statusFilterItems.map(
									(
										statusName: string,
										checkedIndex: number
									) => (
										<ClayCheckbox
											checked={
												checkedStateStatus[
													checkedIndex
												] ?? false
											}
											key={checkedIndex}
											label={
												itemStatus[checkedIndex]?.[0]
													?.item
											}
											onChange={() => {
												handleStatusCheck(
													checkedIndex,
													statusName
												);
											}}
										/>
									)
								)}
							</ClayDropDown.ItemList>
						</ClayDropDown>
					</div>

					<div
						className={classNames(
							'responsive-label flex-wrap w-100 align-items-center',
							{
								'd-flex': filterCheckedLabel.length,
								'd-none': !filterCheckedLabel.length,
							}
						)}
					>
						{filterCheckedLabel.map(
							(currentFilterName: string, index: number) => (
								<ClayLabel
									className="align-items-center d-flex justify-content-center label label-primary mr-2"
									displayType="unstyled"
									key={index}
								>
									<div className="align-items-center d-flex justify-content-center ml-1">
										{`${currentFilterName}`}

										<ClayIcon
											className="cursor-pointer d-flex m-2"
											onClick={() => {
												onClickLabel(currentFilterName);
											}}
											symbol="times"
										/>
									</div>
								</ClayLabel>
							)
						)}

						<div>
							<ClayButton
								className="btn-sm hover-button shadow-none text-neutral-9"
								onClick={() => {
									const updatedCheckedProduct = checkedStateProduct.fill(
										false
									);

									const updatedCheckedStatus = checkedStateStatus.fill(
										false
									);

									setCheckedStateProduct(
										updatedCheckedProduct
									);
									setCheckedStateStatus(updatedCheckedStatus);
									setFilterCheckedLabel([]);
									setFilterProductCheck([]);
									setFilterStatusCheck([]);
									setParameters(generateParameters());
								}}
							>
								<ClayIcon
									className="mr-1"
									symbol="times-circle"
								/>
								Clear
							</ClayButton>
						</div>
					</div>
				</div>
			</div>

			<Table
				actions={[
					{
						action: handleEditPolicy,
						value: 'Edit',
					},
					{
						action: handleDeletePolicy,
						value: 'Delete',
					},
				]}
				data={policies}
				headers={HEADERS}
				onClickRules={onClickRules}
				onSaveCurrent={setHeader}
				setSort={setSortState}
				setSortByOrder={setSortRule}
				sort={sortState}
				sortByOrder={sortPolicyByEndDate}
			/>

			<div className="d-flex justify-content-between mt-3">
				<ClayPaginationBar>
					<ClayPaginationBar.DropDown
						items={[
							{
								label: '5',
								onClick: () => {
									setPageSize(5);
									setPage(1);
								},
							},
							{
								label: '10',
								onClick: () => {
									setPageSize(10);
									setPage(1);
								},
							},
							{
								label: '20',
								onClick: () => {
									setPageSize(20);
									setPage(1);
								},
							},
							{
								label: '30',
								onClick: () => {
									setPageSize(30);
									setPage(1);
								},
							},
							{
								href: '#3',
								label: '50',
								onClick: () => {
									setPageSize(50);
									setPage(1);
								},
							},
							{
								label: '75',
								onClick: () => {
									setPageSize(75);
									setPage(1);
								},
							},
						]}
						trigger={
							<ClayButton displayType="unstyled">
								{pageSize}
								&nbsp;Entries
								<ClayIcon symbol="caret-double-l" />
							</ClayButton>
						}
					/>

					<ClayPaginationBar.Results>
						Showing {firstPaginationLabel}
						&nbsp;to&nbsp;
						{secondPaginationLabel} of {totalCount} entries.
					</ClayPaginationBar.Results>
				</ClayPaginationBar>

				<ClayPaginationWithBasicItems
					activePage={page}
					ellipsisBuffer={2}
					onPageChange={(page: number) => setPage(page)}
					totalPages={totalPages}
				/>
			</div>
		</div>
	);
};

export default PoliciesTable;
