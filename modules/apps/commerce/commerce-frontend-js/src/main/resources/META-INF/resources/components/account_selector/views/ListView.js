/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import Autocomplete from '../../autocomplete/Autocomplete';

function ListView({
	apiUrl,
	contentWrapperRef,
	customView,
	disabled,
	placeholder,
}) {
	return (
		<Autocomplete
			apiUrl={apiUrl}
			contentWrapperRef={contentWrapperRef}
			customView={customView}
			disabled={disabled}
			infiniteScrollMode={true}
			inputName={placeholder}
			inputPlaceholder={Liferay.Language.get(placeholder)}
			itemsKey="id"
			itemsLabel="name"
			pageSize={10}
		/>
	);
}

export default ListView;
