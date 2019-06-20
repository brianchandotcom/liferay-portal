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

import {evaluate} from '../../util/evaluation.es';
import {PagesVisitor} from '../../util/visitors.es';

export default evaluatorContext => {
	return evaluate(null, evaluatorContext).then(evaluatedPages => {
		let validForm = true;
		const visitor = new PagesVisitor(evaluatedPages);

		visitor.mapFields(({valid}) => {
			if (!valid) {
				validForm = false;
			}
		});

		return Promise.resolve(validForm);
	});
};
