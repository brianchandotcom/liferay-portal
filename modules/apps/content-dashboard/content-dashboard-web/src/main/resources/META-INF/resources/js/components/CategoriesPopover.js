/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLabel from '@clayui/label';
import ClayPopover from '@clayui/popover';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

const CategoriesPopover = ({categories, vocabulary}) => {
	const [openPopover, setOpenPopover] = useState(false);

	return (
		<ClayPopover
			alignPosition="top"
			className="categories-popover"
			closeOnClickOutside={true}
			disableScroll={true}
			header={`${categories.length} ${vocabulary} ${Liferay.Language.get(
				'categories'
			)}`}
			onShowChange={setOpenPopover}
			show={openPopover}
			trigger={
				<ClayButton
					className="category-label category-label-see-more label label-lg label-secondary"
					displayType="unstyled"
				>
					{`+ ${categories.length}`}
				</ClayButton>
			}
		>
			{categories.map((cat, index) => (
				<ClayLabel key={index} large={true}>
					{cat}
				</ClayLabel>
			))}
		</ClayPopover>
	);
};

CategoriesPopover.propTypes = {
	categories: PropTypes.array.isRequired,
	vocabulary: PropTypes.string.isRequired,
};

export default CategoriesPopover;
