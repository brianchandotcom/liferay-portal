/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {useId} from 'frontend-js-components-web';
import React from 'react';

import useSetRef from '../../../common/hooks/useSetRef';
import {getLayoutDataItemPropTypes} from '../../../prop_types/index';
import {useGetFieldValue} from '../../contexts/CollectionItemContext';
import {FormStepContextProvider} from '../../contexts/FormStepContext';
import {useSelector, useSelectorCallback} from '../../contexts/StoreContext';
import getLayoutDataItemClassName from '../../utils/getLayoutDataItemClassName';
import getLayoutDataItemCssClasses from '../../utils/getLayoutDataItemCssClasses';
import getLayoutDataItemTopperUniqueClassName from '../../utils/getLayoutDataItemTopperUniqueClassName';
import getLayoutDataItemUniqueClassName from '../../utils/getLayoutDataItemUniqueClassName';
import {getResponsiveConfig} from '../../utils/getResponsiveConfig';
import useBackgroundImageValue from '../../utils/useBackgroundImageValue';
import Topper from '../topper/Topper';

const FormStepContainerWithControls = React.forwardRef(
	({children, item}, ref) => {
		const [setRef, itemElement] = useSetRef(ref);

		return (
			<Topper
				className={getLayoutDataItemTopperUniqueClassName(item.itemId)}
				item={item}
				itemElement={itemElement}
			>
				<FormStepContainer item={item} ref={setRef}>
					{children}
				</FormStepContainer>
			</Topper>
		);
	}
);

FormStepContainerWithControls.displayName = 'FormStepContainer';

FormStepContainerWithControls.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
};

const FormStepContainer = React.forwardRef(({children, item}, ref) => {
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const itemConfig = getResponsiveConfig(item.config, selectedViewportSize);

	const {backgroundImage} = itemConfig.styles;

	const elementId = useId();
	const getFieldValue = useGetFieldValue();
	const backgroundImageValue = useBackgroundImageValue(
		elementId,
		backgroundImage,
		getFieldValue
	);

	const style = {};

	if (backgroundImageValue.url) {
		style[`--lfr-background-image-${item.itemId}`] =
			`url(${backgroundImageValue.url})`;

		if (backgroundImage?.fileEntryId) {
			style['--background-image-file-entry-id'] =
				backgroundImage.fileEntryId;
		}
	}

	const form = useSelectorCallback(
		(state) => state.layoutData.items[item.parentId],
		[item]
	);

	return (
		<FormStepContextProvider form={form}>
			<div
				className={classNames(
					getLayoutDataItemClassName(item.type),
					getLayoutDataItemCssClasses(item),
					getLayoutDataItemUniqueClassName(item.itemId),
					{'custom-height': item.config.styles.height}
				)}
				ref={ref}
				style={style}
			>
				{backgroundImageValue.mediaQueries ? (
					<style>{backgroundImageValue.mediaQueries}</style>
				) : null}

				{children}
			</div>
		</FormStepContextProvider>
	);
});

FormStepContainer.displayName = 'FormStepContainer';

FormStepContainer.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
};

export {FormStepContainer, FormStepContainerWithControls};
