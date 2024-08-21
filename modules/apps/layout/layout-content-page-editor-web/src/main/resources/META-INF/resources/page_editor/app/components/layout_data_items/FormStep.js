/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React, {useEffect, useState} from 'react';

import {getLayoutDataItemPropTypes} from '../../../prop_types/index';
import {useItemLocalConfig} from '../../contexts/LocalConfigContext';
import {useSelectorCallback} from '../../contexts/StoreContext';
import getLayoutDataItemTopperUniqueClassName from '../../utils/getLayoutDataItemTopperUniqueClassName';
import getLayoutDataItemUniqueClassName from '../../utils/getLayoutDataItemUniqueClassName';
import isItemEmpty from '../../utils/isItemEmpty';
import TopperEmpty from '../topper/TopperEmpty';

const FormStepWithControls = React.forwardRef(({children, item}, ref) => {
	const isEmpty = useSelectorCallback(
		(state) =>
			isItemEmpty(item, state.layoutData, state.selectedViewportSize),
		[item]
	);

	const index = useSelectorCallback(
		(state) => {
			return state.layoutData.items[item.parentId]?.children.indexOf(
				item.itemId
			);
		},
		[item]
	);

	const formId = useSelectorCallback(
		(state) => state.layoutData.items[item.parentId]?.parentId,

		[item]
	);

	const localConfig = useItemLocalConfig(formId);

	const [visible, setVisible] = useState(index === 0);

	useEffect(() => {
		const onStepChange = ({emitter, step}) => {
			const form = document.querySelector(
				`.${getLayoutDataItemUniqueClassName(formId)}`
			);

			// Return if the emitter is not in this form

			if (!form.contains(emitter)) {
				return;
			}

			// Change step visibility

			setVisible(step === index);
		};

		Liferay.on('formFragment:changeStep', onStepChange);

		return () => Liferay.detach('formFragment:changeStep', onStepChange);
	}, [formId, index, setVisible]);

	return (
		<TopperEmpty
			className={getLayoutDataItemTopperUniqueClassName(item.itemId)}
			item={item}
		>
			<FormStep
				className={classNames('page-editor__form-step', {
					'd-none': !visible && !localConfig.displayAllSteps,
				})}
				ref={ref}
			>
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

const FormStep = React.forwardRef(({children, className}, ref) => {
	return (
		<div className={className} ref={ref}>
			{children}
		</div>
	);
});

FormStep.displayName = 'FormStep';

FormStep.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
};

export {FormStep, FormStepWithControls};
