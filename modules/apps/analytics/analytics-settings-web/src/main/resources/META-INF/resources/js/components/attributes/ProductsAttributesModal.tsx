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

import Modal, {ICommonModalProps} from './Modal';

const ProductsAttributesModal: React.FC<ICommonModalProps> = ({
	observer,
	onCancel,
	onSubmit,
}) => (
	<Modal
		columns={[
			{
				expanded: true,
				label: Liferay.Language.get('attribute'),
				value: 'attribute',
			},

			{
				expanded: true,
				label: Liferay.Language.get('data-type'),
				value: 'dataType',
			},
			{
				expanded: true,
				label: Liferay.Language.get('sample-data'),
				value: 'sampleData',
			},
		]}
		observer={observer}
		onCancel={onCancel}
		onSubmit={onSubmit}
		requestFn={() => Promise.resolve()}
		title={Liferay.Language.get('sync-product-attributes')}
	/>
);

export default ProductsAttributesModal;
