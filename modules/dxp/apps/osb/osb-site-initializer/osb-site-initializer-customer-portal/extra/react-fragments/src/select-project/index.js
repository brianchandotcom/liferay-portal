/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import React, {useEffect, useMemo, useState} from 'react';

const MAX_ITEM = 10;

const spritemap =
	Liferay.ThemeDisplay.getCDNBaseURL() +
	'/o/admin-theme/images/clay/icons.svg';

const getLiferaySiteName = () => {
	const {pathname} = new URL(Liferay.ThemeDisplay.getCanonicalURL());
	const pathSplit = pathname.split('/').filter(Boolean);
	const siteName = `/${(pathSplit.length > 2
		? pathSplit.slice(0, pathSplit.length - 1)
		: pathSplit
	).join('/')}`;

	return siteName;
};

const handleClickHome = () => {
	const urlHome = `${window.location.origin}${getLiferaySiteName()}`;
	window.location.href = urlHome;
};

export default function () {
	const [items, setItems] = useState([]);
	const [value, setValue] = useState('');
	const selectedExternalReferenceCode = useMemo(() => {
		const liferaySearchParams = new URLSearchParams(window.location.search);

		return liferaySearchParams.get('kor_id');
	}, []);

	const filteredItems =
		items?.filter((item) =>
			item?.label?.toLowerCase().includes(value?.toLowerCase())
		) || [];

	if (!filteredItems.length) {
		filteredItems.push({
			className: 'px-3 my-3 text-neutral-5',
			disabled: true,
			label: 'No projects match that name.',
		});
	}

	const firstItems = items.filter((_, index) => index <= 19);

	const itemName =
		items.find((item) => item.name === selectedExternalReferenceCode)
			?.label || '';

	useEffect(() => {
		Liferay.once(
			'customer-portal-select-user-loading',
			({detail: userAccount}) => {
				const accountBriefs = userAccount.accountBriefs;

				if (accountBriefs) {
					setItems(
						accountBriefs.map(({externalReferenceCode, name}) => {
							const urlLocation = `${
								window.location.origin
							}${getLiferaySiteName()}/overview?kor_id=${externalReferenceCode}`;

							return {
								className: `c-py-3 m-0 ${
									selectedExternalReferenceCode ===
									externalReferenceCode
										? 'selected-item'
										: 'unselect-item'
								}`,
								href: urlLocation,
								label: name,
								name: externalReferenceCode,
								symbolRight:
									selectedExternalReferenceCode ===
									externalReferenceCode
										? 'check'
										: '',
							};
						})
					);
				}
			}
		);
	}, [selectedExternalReferenceCode]);

	return (
		<ClayDropDownWithItems
			alignmentPosition={['tl', 'br']}
			footerContent={
				<div className="all-projects c-py-2">
					<a onClick={handleClickHome}>
						<p className="c-pl-4 my-0 py-2">
							<ClayIcon
								className="mr-2"
								spritemap={spritemap}
								symbol="order-arrow-left"
							/>
							All Projects
						</p>
					</a>
				</div>
			}
			items={value ? filteredItems : firstItems}
			menuElementAttrs={{
				className: `custom-projects-dropdown ${
					filteredItems.length > MAX_ITEM
						? 'show-scroll'
						: 'hide-scroll'
				} c-p-0`,
			}}
			onSearchValueChange={setValue}
			searchProps={{placeholder: 'Search'}}
			searchValue={value}
			searchable={items.length >= MAX_ITEM}
			spritemap={spritemap}
			trigger={
				<ClayButton className="shadow-none" displayType="unstyled">
					<h5 className="m-0">
						<>{`${itemName} `}</>

						<ClayIcon
							className="arrow-down-item"
							spritemap={spritemap}
							symbol="caret-bottom"
						/>
					</h5>
				</ClayButton>
			}
		/>
	);
}
