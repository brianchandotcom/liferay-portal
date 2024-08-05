/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {getLayoutDataItemPropTypes} from '../../../prop_types/index';
import {useSelectorCallback} from '../../contexts/StoreContext';
import getLayoutDataItemTopperUniqueClassName from '../../utils/getLayoutDataItemTopperUniqueClassName';
import isItemEmpty from '../../utils/isItemEmpty';
import TopperEmpty from '../topper/TopperEmpty';

const FormStepWithControls = React.forwardRef(({children, item}, ref) => {
	const isEmpty = useSelectorCallback(
		(state) =>
			isItemEmpty(item, state.layoutData, state.selectedViewportSize),
		[item]
	);

	return (
		<TopperEmpty
			className={getLayoutDataItemTopperUniqueClassName(item.itemId)}
			item={item}
		>
			<FormStep ref={ref}>
				{isEmpty && (
					<div className="page-editor__no-fragments-state">
						<p className="page-editor__no-fragments-state__message">
							{Liferay.Language.get(
								'place-fragments-or-widgets-here'
							)}
						</p>
					</div>
				)}

				{children}
			</FormStep>
		</TopperEmpty>
	);
});

FormStepWithControls.displayName = 'FormStepWithControls';

FormStepWithControls.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
};

const FormStep = React.forwardRef(({children}, ref) => {
	return (
		<div className="page-editor__form-step" ref={ref}>
			{children}
		</div>
	);
});

FormStep.displayName = 'FormStep';

FormStep.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
};

export {FormStep, FormStepWithControls};
