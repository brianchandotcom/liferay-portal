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
import React from 'react';

import {config} from '../../app/config/index';
import {useSelector} from '../../app/contexts/StoreContext';
import selectLanguageId from '../../app/selectors/selectLanguageId';
import getLanguages from '../../app/utils/getLanguages';

export default function CurrentLanguageFlag() {
	const languageId = useSelector(selectLanguageId);

	const languages = getLanguages(config.availableLanguages);

	return (
		<div
			className="align-self-end autofit-col ml-1 p-2"
			data-title={Liferay.Language.get('localizable')}
		>
			<ClayIcon symbol={languages[languageId].languageIcon} />
			<span className="sr-only">
				{languages[languageId].w3cLanguageId}
			</span>
		</div>
	);
}
