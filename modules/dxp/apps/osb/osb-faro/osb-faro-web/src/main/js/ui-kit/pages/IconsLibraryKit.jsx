import ClayIcon from '@clayui/icon';
import Input from 'shared/components/Input';
import Item from 'ui-kit/components/Item';
import Label from 'shared/components/Label';
import React, {useState} from 'react';
import Row from 'ui-kit/components/Row';
import {Sizes} from 'shared/util/constants';

const req = require.context('../../../images', false, /\.svg$/);

const icons = req.keys().map((name, id) => ({
	id,
	name: name.replace('./', '').replace('.svg', '')
}));

const IconLibraryKit = () => {
	const [value, setValue] = useState('');
	const filteredIcons = icons.filter(
		({name}) => !value || name.includes(value)
	);

	return (
		<div className='icons-library-root'>
			<div>
				<Input
					onInput={({target: {value}}) => setValue(value)}
					placeholder='filter by icon name'
				/>
			</div>

			<Row flex>
				{filteredIcons.map(({id, name}) => (
					<Item
						className='col-sm-3 d-flex justify-content-center mx-0 my-4'
						key={id}
					>
						<div className='text-center'>
							<div className='mb-3'>
								<ClayIcon
									className={`icon-root icon-size-${Sizes.XLarge}`}
									symbol={name}
								/>
							</div>

							<Label
								className='d-block m-0'
								display='secondary'
								size='lg'
							>
								{name}
							</Label>
						</div>
					</Item>
				))}
			</Row>
		</div>
	);
};

export default IconLibraryKit;
