/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
import Table from '../../../../common/components/table';
import {Parameters, getPolicies} from '../../../../common/services';
import {
	deleteClaimByExternalReferenceCode,
	getClaims,
} from '../../../../common/services/Claim';
import {getPicklistByName} from '../../../../common/services/Picklists';
import {getProducts} from '../../../../common/services/Products';
import formatDate from '../../../../common/utils/dateFormatter';

type ClaimTableType = {
	claimCreateDate: string;
	claimStatus: {name: string};
	externalReferenceCode: string;
	id: string;
	r_policyToClaims_c_raylifePolicy: {
		externalReferenceCode: string;
		policyOwnerName: string;
		productName: string;
	};
};

type itemsProducts = {
	[keys: string]: string;
};

type itemsPicklists = {
	[keys: string]: string;
};

type TableContentType = {
	[key: string]: string;
};

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
	const [sortByDate, setSortByDate] = useState<string>('desc');
	const [activeFilter, setActiveFilter] = useState(true);
	const [productFilterItems, setProductFilterItems] = useState<string[]>([]);
	const [statusFilterItems, setStatusFilterItems] = useState<string[]>([]);
	const [filterProductCheck, setFilterProductCheck] = useState<string[]>([]);
	const [filterStatusCheck, setFilterStatusCheck] = useState<string[]>([]);
	const [filterCheckedLabel, setFilterCheckedLabel] = useState<string[]>([]);
	const [checkedStateProduct, setCheckedStateProduct] = useState<any>();
	const [checkedStateStatus, setCheckedStateStatus] = useState<any>();
	const [policyERCByPON, setPolicyERCByPON] = useState<string>();
	const [policyERCByProduct, setPolicyERCByProduct] = useState<string[]>([]);

	const filterSearch = `contains(id, '${searchInput}') or contains(r_policyToClaims_c_raylifePolicyERC, '${searchInput}') or contains(r_policyToClaims_c_raylifePolicyERC, '${policyERCByPON}')`;

	const filterProduct = `r_policyToClaims_c_raylifePolicyERC in (${policyERCByProduct})`;

	const filterStatus = `claimStatus in (${filterStatusCheck})`;

	const filterSearchAndStatus = `${filterSearch} and ${filterStatus}`;

	const filterSearchAndProduct = `${filterSearch} and ${filterProduct}`;

	const filterSearchAndStatusAndProduct = `${filterSearch} and ${filterProduct} and ${filterStatus}`;

	const generateParameters = (filtered?: string) => {
		const parameters: Parameters =
			filtered === undefined
				? {
						page: '0',
						pageSize: '0',
						sort: `claimCreateDate:${sortByDate}`,
				  }
				: {
						filter: filtered,
						page: '0',
						pageSize: '0',
						sort: `claimCreateDate:${sortByDate}`,
				  };

		return parameters;
	};

	const [parameters, setParameters] = useState<Parameters>(
		generateParameters()
	);

	const setFilterSearch = () => {
		if (
			searchInput &&
			!filterProductCheck.length &&
			!filterStatusCheck.length &&
			!policyERCByProduct.length
		) {
			return filterSearch;
		}

		if (
			!searchInput &&
			filterProductCheck.length &&
			policyERCByProduct.length
		) {
			return filterProduct;
		}

		if (!searchInput && filterStatusCheck.length) {
			return filterStatus;
		}

		if (searchInput) {
			if (
				filterStatusCheck.length &&
				!filterProductCheck.length &&
				!policyERCByProduct.length
			) {
				return filterSearchAndStatus;
			}
			if (
				filterProductCheck.length &&
				policyERCByProduct.length &&
				!filterStatusCheck.length
			) {
				return filterSearchAndProduct;
			}
			if (
				filterStatusCheck.length &&
				filterProductCheck.length &&
				policyERCByProduct.length
			) {
				return filterSearchAndStatusAndProduct;
			}
		}
	};

	parameters.pageSize = pageSize.toString();
	parameters.page = page.toString();

	const handleChangeSearch = (event: React.ChangeEvent<HTMLInputElement>) => {
		setSearchInput(event.target.value);
	};

	const filterClick = () => {
		setActiveFilter(!activeFilter);
	};

	const getPolicyERCByPolicyOwnerName = async () => {
		const policies = await getPolicies();

		const filterPolicyByPolicyOwnerName = policies?.data?.items?.filter(
			(data: {policyOwnerName: string}) =>
				data?.policyOwnerName === searchInput
		);

		setPolicyERCByPON(
			filterPolicyByPolicyOwnerName[0]?.externalReferenceCode
		);
	};

	const getPolicyERCByProductName = async () => {
		const policies = await getPolicies();

		const policyERCs: string[] = [];

		filterProductCheck.forEach((productCheck) => {
			for (const result of policies?.data?.items) {
				if (productCheck === `'${result?.productName}'`) {
					policyERCs.push(
						"'" + result.r_quoteToPolicies_c_raylifeQuoteERC + "'"
					);
				}
			}
		});

		const newPolicyERCs = [...new Set(policyERCs)];

		setPolicyERCByProduct(newPolicyERCs);
	};

	const HEADERS = [
		{
			greyColor: true,
			hasSort: true,
			key: 'claimCreateDate',
			value: 'Date Field',
		},
		{
			greyColor: true,
			key: 'productName',
			value: 'Product',
		},
		{
			bold: true,
			key: 'id',
			type: 'link',
			value: 'Claim Number',
		},
		{
			greyColor: true,
			key: 'policyNumber',
			value: 'Policy Number',
		},
		{
			greyColor: true,
			key: 'claimName',
			value: 'Name',
		},
		{
			greyColor: true,
			key: 'claimStatus',
			type: 'hasBubble',
			value: 'Status',
		},
	];

	const PARAMETERS = {
		page: '0',
		pageSize: '0',
	};

	PARAMETERS.pageSize = pageSize.toString();
	PARAMETERS.page = page.toString();

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
	};

	useEffect(() => {
		if (!activeFilter) {
			getProducts().then((results) => {
				const productsResult = results?.data?.items;

				const products = productsResult?.map(
					(product: itemsProducts) => {
						return product?.name;
					}
				);

				setProductFilterItems(products);
			});

			getPicklistByName('ClaimStatus').then((results) => {
				const claimStatusResult = results?.data?.listTypeEntries;

				const claimStatuses = claimStatusResult?.map(
					(claimStatusPicklist: itemsPicklists) => {
						return claimStatusPicklist?.name;
					}
				);

				setStatusFilterItems(claimStatuses);
			});

			return;
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [activeFilter]);

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

	const getClaimsAndPolicies = useCallback(async () => {
		const claimList: TableContentType[] = [];

		const results = await getClaims(generateParameters(setFilterSearch()));

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
				key: externalReferenceCode,
				policyNumber:
					r_policyToClaims_c_raylifePolicy?.externalReferenceCode,
				productName: r_policyToClaims_c_raylifePolicy?.productName,
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
		filterSearch,
		page,
		pageSize,
		sortByDate,
		searchInput,
		filterCheckedLabel,
		filterProduct,
		filterStatus,
	]);

	useEffect(() => {
		getClaimsAndPolicies();
		getPolicyERCByPolicyOwnerName();
		getPolicyERCByProductName();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [
		sortByDate,
		getClaimsAndPolicies,
		page,
		pageSize,
		searchInput,
		filterSearch,
		filterCheckedLabel,
		filterProduct,
		filterStatus,
	]);

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

	function convertToCamelCase(str: string) {
		return str
			.replace(/(?:^\w|[A-Z]|\b\w)/g, (word: string, index: number) => {
				return index === 0 ? word.toLowerCase() : word.toUpperCase();
			})
			.replace(/\s+/g, '');
	}

	const checkItemStatus = (statusName: string) => {
		setFilterCheckedLabel((old: any) => [...old, statusName]);
		if (!filterStatusCheck.includes(statusName)) {
			setFilterStatusCheck((old: any) => [
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

	const setSortRule = () => {
		sortByDate === 'desc' ? setSortByDate('asc') : setSortByDate('desc');
	};

	const title = `Claims (${totalCount})`;

	return (
		<div className="px-3">
			<div className="d-flex justify-content-between responsive-search">
				<Header className="mb-5 pt-3" title={title} />

				<ClayForm.Group className="flex-row mt-3 px-3">
					<ClayInput.Group className="justify-content-between">
						<ClayInput.GroupItem prepend>
							<ClayInput
								onChange={handleChangeSearch}
								placeholder="Search for..."
								type="text"
							/>
						</ClayInput.GroupItem>

						<ClayInput.GroupItem append shrink>
							<ClayButton displayType="secondary" type="submit">
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
												]
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
												checkedStateStatus[checkedIndex]
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
					{action: handleEditClaim, value: 'Edit'},
					{
						action: handleDeleteClaim,
						value: 'Delete',
					},
				]}
				data={dataClaims}
				headers={HEADERS}
				setSortByDate={setSortRule}
				sortByDate={sortByDate}
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
