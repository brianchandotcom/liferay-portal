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

import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {useState} from 'react';

type DropDownProps = {
	data: any[];
};

const DropDown: React.FC<DropDownProps> = () => {
	const [value, setValue] = useState('');

	return (
		<ClayDropDownWithItems
			caption={value}
			items={[]}
			onSearchValueChange={() => setValue('teste')}
			spritemap="caret-bottom"
			trigger={
				<div className="align-items-center d-flex">
					<a>
						<ClayIcon
							className="mr-2"
							style={{fontSize: '30px'}}
							symbol="polls"
						/>

						<ClayIcon
							className="mr-2"
							style={{color: 'black', fontSize: '30px'}}
							symbol="caret-bottom"
						/>
					</a>
				</div>
			}
		/>
	);
};

export default DropDown;
