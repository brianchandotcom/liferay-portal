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

import {
	ConfigProvider,
	FormProvider,
	Pages,
	parseProps,
} from 'data-engine-js-components-web';
import {
	activePageReducer,
	pagesStructureReducer,
} from 'data-engine-js-components-web/js/core/reducers/index.es';
import React, {useRef} from 'react';

import FormSettingsModal from './FormSettingsModal';

const FormSettings = (props) => {
	const {onCloseFormSettings, portletNamespace, visibleFormSettings} = props;
	const {config, state} = parseProps(props);

	const containerRef = useRef(null);
	const prevPagesRef = useRef(props.pages);
	const undoPagesRef = useRef(true);

	const serializedSettingsContext = document.querySelector(
		`#${portletNamespace}serializedSettingsContext`
	);

	if (!serializedSettingsContext) {
		return null;
	}

	return (
		<ConfigProvider config={config}>
			<FormProvider
				initialState={{activePage: 0}}
				reducers={[activePageReducer, pagesStructureReducer]}
				value={state}
			>
				<FormSettingsModal
					onCloseFormSettings={onCloseFormSettings}
					prevPagesRef={prevPagesRef}
					serializedSettingsContext={serializedSettingsContext}
					undoPagesRef={undoPagesRef}
					visibleFormSettings={visibleFormSettings}
				>
					<Pages editable={false} ref={containerRef} />
				</FormSettingsModal>
			</FormProvider>
		</ConfigProvider>
	);
};

export default FormSettings;
