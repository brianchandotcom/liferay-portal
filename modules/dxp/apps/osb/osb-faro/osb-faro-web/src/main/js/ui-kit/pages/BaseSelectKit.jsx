import BaseSelect from 'shared/components/BaseSelect';
import Item from '../components/Item';
import Promise from 'metal-promise';
import React from 'react';
import Row from '../components/Row';

const ITEMS = ['bar', 'baz', 'foo'];

const DATA_SOURCE_FN = () => Promise.resolve(ITEMS);
const ITEM_RENDERER = item => item;
const HANDLE_SELECT = item => alert(item);

export default class BaseSelectKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					<Item>
						<BaseSelect
							dataSourceFn={DATA_SOURCE_FN}
							itemRenderer={ITEM_RENDERER}
							onSelect={HANDLE_SELECT}
							placeholder='Search'
						/>
					</Item>

					<Item>
						<BaseSelect
							dataSourceFn={DATA_SOURCE_FN}
							inputValue='foo'
							itemRenderer={ITEM_RENDERER}
							menuTitle='Menu Title'
							onSelect={HANDLE_SELECT}
						/>
					</Item>
				</Row>
			</div>
		);
	}
}
