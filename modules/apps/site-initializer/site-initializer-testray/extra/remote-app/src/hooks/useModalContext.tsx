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

import {Context} from '@clayui/modal';
import {Size} from '@clayui/modal/lib/types';
import {ReactElement, useContext} from 'react';

import Form from '../components/Form';

interface ModalOptions {
	body: ReactElement;
	footer?: ReactElement;
	size: Size;
	title: string;
}

const useModalContext = () => {
	const [state, dispatch] = useContext(Context);

	const onOpenModal = ({body, footer, size, title}: ModalOptions) => {
		dispatch({
			payload: {
				body,
				footer: footer
					? [
							undefined,
							undefined,
							<Form.Footer
								key={4}
								onClose={state.onClose}
								onSubmit={state.onClose}
							/>,
					  ]
					: [],
				header: title,
				size,
			},
			type: 1,
		});
	};

	return {onOpenModal};
};

export default useModalContext;
