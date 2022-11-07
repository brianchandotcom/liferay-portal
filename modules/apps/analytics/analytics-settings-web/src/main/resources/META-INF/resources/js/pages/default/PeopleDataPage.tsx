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

import React from 'react';
<<<<<<<< HEAD:modules/apps/analytics/analytics-settings-web/types/src/main/resources/META-INF/resources/js/components/CreatePropertyModal.d.ts
interface IModalProps {
	observer: any;
	onCloseModal: () => void;
}
declare const CreatePropertyModal: React.FC<IModalProps>;
export default CreatePropertyModal;
========

import BasePage from '../../components/BasePage';
import {IGenericPageProps} from './DefaultPage';

const PeopleDataPage: React.FC<IGenericPageProps> = ({title}) => (
	<BasePage title={title}>
		<div>{title}</div>
	</BasePage>
);

export default PeopleDataPage;
>>>>>>>> e9a7555e (LRAC-12196 Finish People step):modules/apps/analytics/analytics-settings-web/src/main/resources/META-INF/resources/js/pages/default/PeopleDataPage.tsx
