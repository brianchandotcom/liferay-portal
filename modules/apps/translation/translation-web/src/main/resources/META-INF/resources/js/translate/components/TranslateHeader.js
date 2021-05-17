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

import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import React from 'react';

const TranslateHeader = ({sourceLanguageIdTitle, targetLanguageIdTitle}) => (
	<ClayLayout.Row className="row-autotranslate-title">
		<ClayLayout.Col md={6}>
			<ClayIcon symbol={sourceLanguageIdTitle.toLowerCase()} />
			<span className="ml-2">{sourceLanguageIdTitle}</span>
			<div className="separator" />
		</ClayLayout.Col>
		<ClayLayout.Col md={6}>
			<ClayIcon symbol={targetLanguageIdTitle.toLowerCase()} />
			<span className="ml-2">{targetLanguageIdTitle}</span>
			<div className="separator" />
		</ClayLayout.Col>
	</ClayLayout.Row>
);

export default TranslateHeader;
