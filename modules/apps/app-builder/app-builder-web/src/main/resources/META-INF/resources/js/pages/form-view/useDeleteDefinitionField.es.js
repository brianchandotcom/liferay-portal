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

import {DataLayoutVisitor} from 'data-engine-taglib';
import {PagesVisitor} from 'dynamic-data-mapping-form-renderer';
import {useContext} from 'react';

import FormViewContext from './FormViewContext.es';
import {deleteDefinitionField} from './actions.es';

export default ({dataLayoutBuilder}) => {
	const [{dataLayout}, dispatch] = useContext(FormViewContext);

	return fieldName => {
		const {pages} = dataLayoutBuilder.getStore();
		const visitor = new PagesVisitor(pages);

		if (visitor.containsField(fieldName)) {
			const indexes = DataLayoutVisitor.getIndexesFromFieldName(
				dataLayout,
				fieldName
			);

			dataLayoutBuilder.dispatch('fieldDeleted', {indexes});
		} else {
			dispatch(deleteDefinitionField(fieldName));
		}
	};
};
