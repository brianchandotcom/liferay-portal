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
import {useCallback, useEffect, useState} from 'react';

import Header from '../../../../common/components/header';
import Table, {
	TableHeaders,
	TableRowContentType,
} from '../../../../common/components/table';
import {Parameters} from '../../../../common/services';
import {
	deleteClaimByExternalReferenceCode,
	getClaims,
} from '../../../../common/services/Claim';
import {getPicklistByName} from '../../../../common/services/Picklists';
import {getProducts} from '../../../../common/services/Products';
import {
	Liferay,
	LiferayOnAction,
} from '../../../../common/services/liferay/liferay';
import {
	capitalizeFirstLetter,
	lowercaseFirstLetter,
} from '../../../../common/utils/constantsType';
import formatDate from '../../../../common/utils/dateFormatter';
import {redirectTo} from '../../../../common/utils/liferay';
import useDebounce from '../../../../hooks/useDebounce';

type ClaimTableType = {
	claimCreateDate: string;
	claimStatus: {name: string};
	externalReferenceCode: string;
	id: string;
	isClickable: boolean;
	r_policyToClaims_c_raylifePolicy: {
		externalReferenceCode: string;
		policyOwnerName: string;
		productName: string;
	};
};

type TableContentType = {
	[key: string]: string | any;
};

type ItemsProducts = TableContentType;

type ItemsPicklists = TableContentType;

type ItemsFilteredType = {
	checked: boolean;
	item: string;
};

type StateSortType = {
	[keys: string]: boolean;
};

enum Order {
	Ascendant = 'asc',
	Descendant = 'desc',
}
type ActionType = {eventName: string};

const ClaimsTable = () => {
	const [dataClaims, setDataClaims] = useState<TableContentType[]>([]);
	const [totalPages, setTotalPages] = useState<number>(0);
	const [pageSize, setPageSize] = useState<number>(20);
	const [totalCount, setTotalCount] = useState<number>(0);
	const [page, setPage] = useState<number>(1);
	const [firstPaginationLabel, setFirstPaginationLabel] = useState<number>(1);
	const [secondPaginationLabel, setSecondPaginationLabel] = useState<number>(
		1
	);
	const [searchInput, setSearchInput] = useState('');
	const [sortedOrder, setSortedOrder] = useState<string>(Order.Descendant);
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

	const [currentSort, setCurrentSort] = useState<string>('claimCreateDate');

	const [sortState, setSortState] = useState<StateSortType>({
		claimCreateDate: true,
		claimStatus: false,
		id: false,
		policyNumber: false,
		policyOwnerName: false,
		productName: false,
	});
	const [isRemaining, setIsRemaining] = useState<boolean>(false);

	const filterSearch = `contains(id, '${searchInput}') or contains(r_policyToClaims_c_raylifePolicyERC, '${searchInput}')`;

	const filterProduct = `r_policyToClaims_c_raylifePolicyERC in ()`;

	const filterStatus = `claimStatus in (${filterStatusCheck})`;

	const filterSearchAndStatus = `${filterSearch} and ${filterStatus}`;

	const filterSearchAndProduct = `${filterSearch} and ${filterProduct}`;

	const filterProductAndStatus = `${filterProduct} and ${filterStatus}`;

	const filterSearchAndStatusAndProduct = `${filterSearch} and ${filterProduct} and ${filterStatus}`;

	const pageAndPageSize = {
		page: page.toString(),
		pageSize: pageSize.toString(),
	};

	enum ClaimsChartStatuses {
		Remaining = 'Remaining',
		Settled = 'Settled',
	}
	enum ClaimsChartTypes {
		SettledClaims = 'settledClaims',
		TotalClaims = 'totalClaims',
	}

	const generateParameters = (filtered?: string) => {
		const parameters: Parameters =
			filtered === undefined
				? {
						page: pageAndPageSize?.page,
						pageSize: pageAndPageSize?.pageSize,
						sort: `${currentSort}:${sortedOrder}`,
				  }
				: {
						filter: filtered,
						page: pageAndPageSize?.page,
						pageSize: pageAndPageSize?.pageSize,
						sort: `${currentSort}:${sortedOrder}`,
				  };

		return parameters;
	};

	const [parameters, setParameters] = useState<Parameters>(
		generateParameters()
	);
	const parameterDebounce = useDebounce(parameters, 200);

	parameters.pageSize = pageSize.toString();
	parameters.page = page.toString();

	const setFilterSearch = () => {
		setPage(1);

		if (searchInput) {
			if (!filterProductCheck.length && !filterStatusCheck.length) {
				return setParameters(generateParameters(filterSearch));
			}
			if (filterStatusCheck.length && !filterProductCheck.length) {
				return setParameters(generateParameters(filterSearchAndStatus));
			}
			if (filterProductCheck.length && !filterStatusCheck.length) {
				return setParameters(
					generateParameters(filterSearchAndProduct)
				);
			}
			if (filterStatusCheck.length && filterProductCheck.length) {
				return setParameters(
					generateParameters(filterSearchAndStatusAndProduct)
				);
			}
		}
		if (!searchInput) {
			setParameters(generateParameters());

			if (!filterProductCheck.length && !filterStatusCheck.length) {
				return setParameters(generateParameters(filterSearch));
			}

			if (!filterProductCheck.length && filterStatusCheck.length) {
				return setParameters(generateParameters(filterStatus));
			}
			if (!filterStatusCheck.length && filterProductCheck.length) {
				return setParameters(generateParameters(filterProduct));
			}
			if (filterProductCheck.length && filterStatusCheck.length) {
				return setParameters(
					generateParameters(filterProductAndStatus)
				);
			}
		}
	};

	const handleClick = () => {
		setFilterSearch();
	};

	const handleKeyDown = (event: React.KeyboardEvent<HTMLElement>) => {
		if (event.key === 'Enter') {
			handleClick();
		}
	};

	const handleChangeSearch = (event: React.ChangeEvent<HTMLInputElement>) => {
		setSearchInput(event.target.value);
	};

	const filterClick = () => {
		setActiveFilter(!activeFilter);
	};

	const HEADERS = [
		{
			clickableSort: true,
			greyColor: true,
			hasSort: true,
			key: 'claimCreateDate',
			requestLabel: 'claimCreateDate',
			value: 'Date Field',
		},
		{
			clickableSort: false,
			greyColor: true,
			hasSort: false,
			key: 'productName',
			requestLabel: 'productName',
			value: 'Product',
		},
		{
			bold: true,
			clickable: true,
			clickableSort: true,
			hasSort: false,
			key: 'id',
			requestLabel: 'id',
			type: 'link',
			value: 'Claim Number',
		},
		{
			clickableSort: true,
			greyColor: true,
			hasSort: false,
			key: 'policyNumber',
			requestLabel: 'r_policyToClaims_c_raylifePolicyERC',
			value: 'Policy Number',
		},
		{
			clickableSort: false,
			greyColor: true,
			hasSort: false,
			key: 'claimName',
			requestLabel: 'policyOwnerName',
			value: 'Name',
		},
		{
			clickableSort: true,
			greyColor: true,
			hasSort: false,
			key: 'claimStatus',
			requestLabel: 'claimStatus',
			type: 'hasBubble',
			value: 'Status',
		},
	];

	const handleDeleteClaim = (externalReferenceCode: string) => {
		deleteClaimByExternalReferenceCode(externalReferenceCode);

		const filteredClaims = dataClaims.filter(
			(claim) => !(claim?.key === externalReferenceCode)
		);

		setDataClaims(filteredClaims);
		setTotalCount(totalCount - 1);
	};

	const handleEditClaim = (externalReferenceCode: string) => {
		alert(`Edit ${externalReferenceCode} Action`);
		searchInput;
	};

	useEffect(() => {
		getProducts().then((results) => {
			const productsResult = results?.data?.items;

			const products = productsResult?.map((product: ItemsProducts) => {
				return product?.name;
			});

			setProductFilterItems(products);
		});

		getPicklistByName('ClaimStatus').then((results) => {
			const claimStatusResult = results?.data?.listTypeEntries;

			const claimStatuses = claimStatusResult?.map(
				(claimStatusPicklist: ItemsPicklists) => {
					return claimStatusPicklist?.name;
				}
			);

			setStatusFilterItems(claimStatuses);
		});

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const itemsCreate = (listItem: string[], checkedItem: boolean[]) => {
		const itemsFilters = listItem.map(
			(statusName: string, index: number) => {
				const item: ItemsFilteredType[] = [];
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
	}, [productFilterItems, statusFilterItems]);

	useEffect(() => {
		setFilterSearch();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [
		filterProductCheck,
		filterStatusCheck,
		filterCheckedLabel,
		currentSort,
	]);

	const getClaimsAndPolicies = useCallback(async () => {
		const claimList: TableContentType[] = [];

		const results = await getClaims(parameterDebounce);

		for (const result of results?.data?.items as ClaimTableType[]) {
			const {
				claimCreateDate,
				claimStatus,
				externalReferenceCode,
				id,
				r_policyToClaims_c_raylifePolicy,
			} = result;

			claimList.push({
				claimCreateDate: formatDate(new Date(claimCreateDate), true),
				claimName: r_policyToClaims_c_raylifePolicy?.policyOwnerName,
				claimStatus: claimStatus?.name,
				id,
				isClickable:
					r_policyToClaims_c_raylifePolicy.productName === 'Auto'
						? true
						: false,
				key: externalReferenceCode,
				policyNumber:
					r_policyToClaims_c_raylifePolicy?.externalReferenceCode,
				productName: r_policyToClaims_c_raylifePolicy.productName,
			});
		}

		setDataClaims(claimList);

		const totalCount = results?.data?.totalCount;
		setTotalCount(totalCount);

		const totalPages = Math.ceil(totalCount / pageSize);
		setTotalPages(totalPages);

		const firstPaginationLabel = (page - 1) * pageSize + 1;
		setFirstPaginationLabel(firstPaginationLabel);

		const secondPaginationLabel =
			totalCount > page * pageSize ? page * pageSize : totalCount;
		setSecondPaginationLabel(secondPaginationLabel);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [
		dataClaims,
		page,
		pageSize,
		sortedOrder,
		searchInput,
		filterSearch,
		filterProduct,
		filterStatus,
		filterProductCheck,
		filterStatusCheck,
		parameterDebounce,
	]);

	useEffect(() => {
		getClaimsAndPolicies();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [
		page,
		pageSize,
		sortedOrder,
		searchInput,
		filterSearch,
		filterProduct,
		filterStatus,
		filterProductCheck,
		filterStatusCheck,
		parameterDebounce,
	]);

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

	function convertToCamelCase(str: string) {
		return str
			.replace(/(?:^\w|[A-Z]|\b\w)/g, (word: string, index: number) => {
				return index === 0 ? word.toLowerCase() : word.toUpperCase();
			})
			.replace(/\s+/g, '');
	}

	const checkItemStatus = (statusName: string) => {
		setFilterCheckedLabel((old: string[]) => [...old, statusName]);
		if (!filterStatusCheck.includes(statusName)) {
			setFilterStatusCheck((old: string[]) => [
				...old,
				`'${convertToCamelCase(statusName)}'`,
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
				return (
					currentStatusName !== `'${convertToCamelCase(statusName)}'`
				);
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

	const handleSortParameters = (filter: string) => {
		setSortedOrder(filter);
		setParameters((previous) => ({
			...previous,
			sort: `${currentSort}:${filter}`,
		}));
	};

	const setSortRule = () => {
		sortedOrder === Order.Descendant
			? handleSortParameters(Order.Ascendant)
			: handleSortParameters(Order.Descendant);
	};

	const setHeader = (user: string) => {
		setCurrentSort(user);
		setSortRule();
	};

	const title = `Claims (${totalCount})`;

	// eslint-disable-next-line react-hooks/exhaustive-deps
	const genericUpdateCheckedStatus = (
		currentFilterName: string,
		checkedArray: boolean[],
		items: ItemsFilteredType[][],
		chartType: string
	) => {
		const isChecked = checkedArray.map(
			(checked: boolean, index: number) => {
				const filteredArray = items[index]?.[0]?.item;

				if (currentFilterName === filteredArray) {
					return !checked;
				}

				return checked;
			}
		);

		const isRemainingStatuses =
			currentFilterName !== ClaimsChartStatuses.Settled;

		const checkRemainingStatus = checkedArray.fill(
			true,
			0,
			checkedArray.length - 1
		);

		if (chartType === ClaimsChartTypes.SettledClaims) {
			if (!isRemainingStatuses) {
				return setCheckedStateStatus(isChecked);
			}

			return setCheckedStateStatus(checkRemainingStatus);
		}

		if (chartType === ClaimsChartTypes.TotalClaims) {
			return setCheckedStateProduct(isChecked);
		}
	};

	const handleApplyFilter = (statuses: string) => {
		setFilterCheckedLabel((prevFilterCheckedLabels: string[]) => [
			...prevFilterCheckedLabels,
			statuses,
		]);

		setActiveFilter(false);

		genericUpdateCheckedStatus(
			statuses,
			checkedStateStatus,
			itemStatus,
			ClaimsChartTypes.SettledClaims
		);

		const claimStatusFieldKey = lowercaseFirstLetter(
			statuses.replace(' ', '')
		);

		setFilterStatusCheck((prevFilterStatusCheck: string[]) => [
			...prevFilterStatusCheck,
			`'${claimStatusFieldKey}'`,
		]);
	};

	const handleRedirectToDetailsPages = (id: number, entity: string) => {
		redirectTo(`${entity}?id=${id}`);
	};

	const onClickRules = (
		item: TableHeaders,
		rowContent: TableRowContentType
	) => {
		const hasDetails =
			item.clickable &&
			item.key === 'id' &&
			rowContent.productName === 'Auto';

		if (hasDetails) {
			handleRedirectToDetailsPages(
				rowContent['id'] as number,
				'claim-details'
			);
		}
	};

	useEffect(() => {
		const handler: LiferayOnAction<ActionType> = ({eventName}) => {
			const hasDoubleClick = filterCheckedLabel.some(
				(productName: string) => productName === eventName
			);

			if (!hasDoubleClick) {
				setFilterCheckedLabel((prevFilterCheckedLabels: string[]) => [
					...prevFilterCheckedLabels,
					eventName,
				]);
				setFilterProductCheck((prevFilterProductsCheck: string[]) => [
					...prevFilterProductsCheck,
					`'${eventName}'`,
				]);
				setActiveFilter(false);

				genericUpdateCheckedStatus(
					eventName,
					checkedStateProduct,
					itemProducts,
					ClaimsChartTypes.TotalClaims
				);
			}
		};

		Liferay.on<ActionType>('openSettingsFilterClaimsEvent', handler);

		return () =>
			Liferay.detach<ActionType>(
				'openSettingsFilterClaimsEvent',
				handler
			);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [productFilterItems, checkedStateProduct, filterCheckedLabel]);

	useEffect(() => {
		const handler: LiferayOnAction<ActionType> = ({eventName}) => {
			const capitalized = capitalizeFirstLetter(eventName);

			const hasSettledClick = filterCheckedLabel.some(
				(status: string) => status === capitalized
			);

			if (capitalized === ClaimsChartStatuses.Remaining && !isRemaining) {
				statusFilterItems.map((claimStatus: string) => {
					if (claimStatus !== ClaimsChartStatuses.Settled) {
						handleApplyFilter(claimStatus);

						setIsRemaining(true);
					}
				});
			}

			if (
				!hasSettledClick &&
				capitalized !== ClaimsChartStatuses.Remaining
			) {
				handleApplyFilter(capitalized);
			}
		};

		Liferay.on<ActionType>('openSettingsFilterClaimsSettledEvent', handler);

		return () =>
			Liferay.detach<ActionType>(
				'openSettingsFilterClaimsSettledEvent',
				handler
			);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [statusFilterItems, checkedStateStatus, filterCheckedLabel]);

	return (
		<div className="px-3">
			<div className="d-flex justify-content-between responsive-search">
				<Header className="mb-5 pt-3" title={title} />

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
									setIsRemaining(false);
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
					{action: handleEditClaim, value: 'Edit'},
					{
						action: handleDeleteClaim,
						value: 'Delete',
					},
				]}
				data={dataClaims}
				headers={HEADERS}
				onClickRules={onClickRules}
				onSaveCurrent={setHeader}
				setSort={setSortState}
				sort={sortState}
				sortByOrder={sortedOrder}
			/>

			<div className="d-flex justify-content-between mt-3 px-3">
				<ClayPaginationBar>
					<ClayPaginationBar.DropDown
						items={[
							{
								label: '20',
								onClick: () => {
									setPageSize(20);
									setPage(1);
								},
							},
							{
								label: '40',
								onClick: () => {
									setPageSize(40);
									setPage(1);
								},
							},
							{
								label: '60',
								onClick: () => {
									setPageSize(60);
									setPage(1);
								},
							},
							{
								label: '80',
								onClick: () => {
									setPageSize(80);
									setPage(1);
								},
							},
							{
								href: '#3',
								label: '100',
								onClick: () => {
									setPageSize(100);
									setPage(1);
								},
							},
							{
								label: '120',
								onClick: () => {
									setPageSize(120);
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

export default ClaimsTable;
