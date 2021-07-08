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
import classNames from 'classnames';
import React from 'react';

import {config} from '../../app/config/index';
import {useSelector} from '../../app/contexts/StoreContext';
import selectLanguageId from '../../app/selectors/selectLanguageId';

export default function CurrentLanguageFlag({className}) {
	const languageId = useSelector(selectLanguageId);
	const language = config.availableLanguages[languageId];

	return (
		<div
			className={classNames(className, 'link-monospaced')}
			data-title={Liferay.Language.get('localizable')}
		>
			<ClayIcon symbol={language.languageIcon} />
			<span className="sr-only">{language.w3cLanguageId}</span>
		</div>
	);
}
