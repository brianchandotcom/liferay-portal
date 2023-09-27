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
import React, {useEffect, useState} from 'react';

import Header from '../../../../common/components/header';
import Table, {TableRowContentType} from '../../../../common/components/table';
import {
	deleteApplicationByExternalReferenceCode,
	getAllApplications,
	getApplications,
} from '../../../../common/services';
import {getPicklistByName} from '../../../../common/services/Picklists';
import {getProducts} from '../../../../common/services/Products';
import {Parameters} from '../../../../common/services/index';
import formatDate from '../../../../common/utils/dateFormatter';
import {redirectTo} from '../../../../common/utils/liferay';
import useDebounce from '../../../../hooks/useDebounce';

import './index.scss';

const HEADERS = [
	{
		clickable: true,
		clickableSort: false,
		greyColor: true,
		key: 'applicationCreateDate',
		requestLabel: 'applicationCreateDate',
		type: 'link',
		value: 'Date Filed',
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
		value: 'Application Number',
	},
	{
		clickableSort: false,
		key: 'fullName',
		requestLabel: 'fullName',
		value: 'Name',
	},
	{
		clickable: true,
		clickableSort: false,
		greyColor: true,
		key: 'email',
		requestLabel: 'email',
		type: 'link',
		value: 'Email Address',
	},
	{
		clickableSort: false,
		greyColor: true,
		key: 'name',
		requestLabel: 'name',
		type: 'hasBubble',
		value: 'Status',
	},
];

type Application = {
	applicationCreateDate: Date;
	applicationNumber: number;
	applicationStatus: {name: string};
	email: string;
	externalReferenceCode: string;
	firstName: string;
	lastName: string;
	productName: string;
};

type TableContent = {[keys: string]: string | any};

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

type itemsApplicationsFilter = {
	applicationStatus: {name: string};
	productName: string;
};

type itemsApplications = TableContent;

type itemsProducts = TableContent;

type itemsPicklists = TableContent;

type StateSortType = {
	[keys: string]: boolean;
};

const STATUS_EDIT_DISABLED = ['Bound', 'Quoted'];

const STATUS_DELETE_DISABLED = [
	'Bound',
	'Quoted',
	'Rejected',
	'Reviewed',
	'Underwriting',
];

const CLICKBLE_STATUS: TableRowContentType = {
	Bound: 'Bound',
	Incomplete: 'Incomplete',
	Rejected: 'Rejected',
	Reviewed: 'Reviewed',
};

const ApplicationsTable = () => {
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

	const [applications, setApplications] = useState<TableContent[]>([]);
	const [totalCount, setTotalCount] = useState<number>(0);
	const [pageSize, setPageSize] = useState<number>(5);
	const [totalPages, setTotalPages] = useState<number>(0);
	const [page, setPage] = useState<number>(1);
	const [firstPaginationLabel, setFirstPaginationLabel] = useState<number>(1);
	const [secondPaginationLabel, setSecondPaginationLabel] = useState<number>(
		1
	);

	const [searchInput, setSearchInput] = useState('');
	const firstNameSearched = searchInput.split(' ')[0];
	const lastNameSearched = searchInput.split(' ')[1];
	const [activeFilter, setActiveFilter] = useState(true);
	const [productFilterItems, setProductFilterItems] = useState<string[]>([]);
	const [statusFilterItems, setStatusFilterItems] = useState<string[]>([]);
	const [filterProductCheck, setFilterProductCheck] = useState<string[]>([]);
	const [filterStatusCheck, setFilterStatusCheck] = useState<string[]>([]);
	const [filterCheckedLabel, setFilterCheckedLabel] = useState<string[]>([]);
	const [checkedStateProduct, setCheckedStateProduct] = useState<
		boolean[] | any
	>([]);
	const [checkedStateStatus, setCheckedStateStatus] = useState<
		boolean[] | any
	>([]);

	const filterSearch = !lastNameSearched
		? `contains(firstName,'${firstNameSearched}') or contains(lastName,'${firstNameSearched}') or contains(email, '${firstNameSearched}') or contains(externalReferenceCode, '${firstNameSearched}')`
		: `contains(firstName,'${firstNameSearched}') and contains(lastName,'${lastNameSearched}')`;

	const filterProduct = !filterStatusCheck.length
		? `productName in (${filterProductCheck})`
		: `applicationStatus in (${filterStatusCheck}) and productName in (${filterProductCheck})`;

	const filterStatus = !filterProductCheck.length
		? `applicationStatus in (${filterStatusCheck})`
		: `applicationStatus in (${filterStatusCheck}) and productName in (${filterProductCheck})`;

	const generateParameters = (filtered?: string) => {
		const parameters: Parameters =
			filtered === undefined
				? {page: '0', pageSize: '0', sort: 'applicationCreateDate:desc'}
				: {
						filter: filtered,
						page: '0',
						pageSize: '0',
						sort: 'applicationCreateDate:desc',
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

	const handleDeleteApplication = (externalReferenceCode: string) => {
		deleteApplicationByExternalReferenceCode(externalReferenceCode);

		const filteredApplications = applications.filter(
			(application) => !(application.key === externalReferenceCode)
		);

		setApplications(filteredApplications);
		setTotalCount(totalCount - 1);
	};

	const handleEditApplication = (externalReferenceCode: string) => {
		alert(`Edit ${externalReferenceCode} Action`);
	};

	const setDisabledEditAction = (externalReferenceCode: string) => {
		const application = applications.find(
			(application) => application.key === externalReferenceCode
		) as TableContent;

		return STATUS_EDIT_DISABLED.includes(application.name);
	};

	const setDisabledDeleteAction = (externalReferenceCode: string) => {
		const application = applications.find(
			(application) => application.key === externalReferenceCode
		) as TableContent;

		return STATUS_DELETE_DISABLED.includes(application.name);
	};

	const itemsCreate = (listItem: string[], checkedItem: []) => {
		const itemsFilters = listItem.map(
			(statusName: string, index: number) => {
				const item: any = [];
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
		if (!activeFilter) {
			conditionalFilters();
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [filterProductCheck, filterStatusCheck, filterCheckedLabel]);

	useEffect(() => {
		if (!activeFilter) {
			getAllApplications(generateParameters()).then((results) => {
				const allItems: itemsApplications[] = [];
				results?.data?.items?.forEach(
					({
						applicationStatus: {name},
						productName,
					}: itemsApplicationsFilter) => {
						allItems.push({
							name,
							productName,
						});
					}
				);

				getProducts().then((results) => {
					const productsResult = results?.data?.items;

					const products = productsResult?.map(
						(product: itemsProducts) => {
							return product.name;
						}
					);

					setProductFilterItems(products);
				});

				getPicklistByName('ApplicationStatus').then((results) => {
					const applicationStatusResult =
						results?.data?.listTypeEntries;

					const applicationStatuses = applicationStatusResult?.map(
						(applicationStatusPicklist: itemsPicklists) => {
							return applicationStatusPicklist.name;
						}
					);

					setStatusFilterItems(applicationStatuses);
				});
			});

			return;
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [activeFilter]);

	useEffect(() => {
		getApplications(parameterDebounce).then((results) => {
			const applicationsList: TableContent[] = [];
			results?.data?.items.forEach(
				({
					applicationCreateDate,
					applicationStatus: {name},
					email,
					externalReferenceCode,
					firstName,
					lastName,
					productName,
				}: Application) => {
					const fullName = firstName + ' ' + lastName;

					applicationsList.push({
						applicationCreateDate: formatDate(
							new Date(applicationCreateDate),
							true
						),
						email,
						externalReferenceCode,
						fullName,
						isClickable: true,
						key: externalReferenceCode,
						name,
						productName,
					});
				}
			);

			setApplications(applicationsList);

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

	const title = `Applications (${totalCount})`;

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
			item.clickable &&
			CLICKBLE_STATUS[rowContent['name']] &&
			(item.key === 'externalReferenceCode' ||
				item.key === 'applicationCreateDate')
		) {
			handleRedirectToDetailsPages(
				rowContent['externalReferenceCode'] as string,
				'app-details'
			);
		}
	};

	const handleKeyDown = (event: any) => {
		if (event.key === 'Enter') {
			handleClick();
		}
	};

	const checkItemProduct = (productCheck: string) => {
		setFilterCheckedLabel((old: any) => [...old, productCheck]);

		if (!filterProductCheck.includes(productCheck)) {
			setFilterProductCheck((old: any) => [...old, `'${productCheck}'`]);
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
		setFilterCheckedLabel((old: any) => [...old, statusName]);
		if (!filterStatusCheck.includes(statusName)) {
			setFilterStatusCheck((old: any) => [
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

	return (
		<div className="px-3">
			<div className="d-flex justify-content-between responsive-search">
				<Header className="mb-5 pt-3" title={title} />

				<ClayForm.Group className="flex-row mt-3">
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
							aria-label="Search"
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
				<div className="d-flex justify-content-center responsive-filter">
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
									<div className="align-items-center d-flex justify-content-center">
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
						action: handleEditApplication,
						disabled: setDisabledEditAction,
						value: 'Edit',
					},
					{
						action: handleDeleteApplication,
						disabled: setDisabledDeleteAction,
						value: 'Delete',
					},
				]}
				data={applications}
				headers={HEADERS}
				onClickRules={onClickRules}
				setSort={setSortState}
				sort={sortState}
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

export default ApplicationsTable;
