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

import {PagesVisitor} from 'data-engine-js-components-web';
import {useContext} from 'react';

import FormViewContext from './FormViewContext.es';
import {deleteDefinitionField} from './actions.es';

export default ({dataLayoutBuilder}) => {
	const [, dispatch] = useContext(FormViewContext);

	return (event) => {
		const {
			pages,
		} = dataLayoutBuilder.formBuilderWithLayoutProvider.refs.layoutProvider.state;
		const visitor = new PagesVisitor(pages);

		if (visitor.containsField(event.fieldName, true)) {
			dataLayoutBuilder.formBuilderWithLayoutProvider.refs.layoutProvider?.dispatch?.(
				'fieldDeleted',
				event
			);
		}
		else {
			dispatch(deleteDefinitionField(event.fieldName));
		}
	};
};
