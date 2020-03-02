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

import './ParagraphRegister.soy';

import React from 'react';

import {FieldBaseProxy} from '../FieldBase/ReactFieldBase.es';
import getConnectedReactComponentAdapter from '../util/ReactComponentAdapter.es';
import {connectStore} from '../util/connectStore.es';
import templates from './ParagraphAdapter.soy';

const Paragraph = ({name, text}) => (
	<div
		className="form-group liferay-ddm-form-field-paragraph"
		data-field-name={name}
	>
		<div
			className="liferay-ddm-form-field-paragraph-text"
			dangerouslySetInnerHTML={{
				__html: typeof text === 'object' ? text.content : text,
			}}
		/>
	</div>
);

const ParagraphProxy = connectStore(({name, text, ...otherProps}) => (
	<FieldBaseProxy {...otherProps} name={name}>
		<Paragraph name={name} text={text} />
	</FieldBaseProxy>
));

const ReactParagraphAdapter = getConnectedReactComponentAdapter(
	ParagraphProxy,
	templates
);

export {ReactParagraphAdapter};
export default ReactParagraphAdapter;
