/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

import FormSettingsApi from './FormSettingsApi';
import FormSettingsModal from './FormSettingsModal';

const FormSettings = (props) => {
	const {onCloseFormSettings, portletNamespace, visibleFormSettings} = props;
	const {config, state} = parseProps(props);

	const apiRef = useRef(null);
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

				<FormSettingsApi ref={apiRef} />
			</FormProvider>
		</ConfigProvider>
	);
};

export default FormSettings;
