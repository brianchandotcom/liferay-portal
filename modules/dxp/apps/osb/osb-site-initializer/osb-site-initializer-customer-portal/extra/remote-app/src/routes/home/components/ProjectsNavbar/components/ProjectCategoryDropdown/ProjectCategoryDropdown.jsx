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

import {Button, DropDown} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import {useState} from 'react';
import i18n from '~/common/I18n';
import {Skeleton} from '~/common/components';
import getKebabCase from '~/common/utils/getKebabCase';
import {PROJECT_CATEGORY_GROUPS} from '~/routes/customer-portal/utils/constants/projectCategoryGroups';

const ProjectCategoryDropdown = ({loading}) => {
	const [active, setActive] = useState(false);
	const [selectedItemIndex, setSelectedItemIndex] = useState(0);

	const items = [
		{
			label: PROJECT_CATEGORY_GROUPS.teamMember,
		},
		{
			label: PROJECT_CATEGORY_GROUPS.liferayContact,
		},
		{
			label: PROJECT_CATEGORY_GROUPS.flsPartner,
		},
		{
			label: PROJECT_CATEGORY_GROUPS.allOtherProjects,
		},
	];

	const handleOnSelect = (currentIndex) => {
		setSelectedItemIndex(currentIndex);
	};

	return (
		<div className="align-items-center d-flex ml-4 mt-2">
			<div className="font-weight-bold pr-1 text-paragraph-sm">
				{loading ? (
					<Skeleton height={18} width={40} />
				) : (
					`${i18n.translate('projects')}:`
				)}
			</div>

			<DropDown
				active={active}
				closeOnClickOutside
				menuWidth="shrink"
				onActiveChange={setActive}
				trigger={
					<Button
						borderless
						className="align-items-center d-flex px-2"
						disabled={loading}
						small
					>
						{loading ? (
							<Skeleton height={18} width={46} />
						) : (
							i18n.translate(
								getKebabCase(items[selectedItemIndex].label)
							)
						)}

						<span className="inline-item inline-item-after">
							<ClayIcon symbol="caret-bottom" />
						</span>
					</Button>
				}
			>
				{items.map((item, index) => (
					<DropDown.Item
						className="pr-6"
						disabled={index === selectedItemIndex}
						key={`${index}-${index}`}
						onClick={() => {
							handleOnSelect(index);
							setActive(false);
						}}
						symbolRight={index === selectedItemIndex && 'check'}
					>
						{i18n.translate(getKebabCase(item.label))}
					</DropDown.Item>
				))}
			</DropDown>
		</div>
	);
};

export default ProjectCategoryDropdown;
