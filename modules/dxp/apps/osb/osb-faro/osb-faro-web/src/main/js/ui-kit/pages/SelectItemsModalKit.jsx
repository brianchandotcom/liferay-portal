import Promise from 'metal-promise';
import React from 'react';
import SelectItemsModal from 'shared/components/modals/SelectItemsModal';

const ITEMS = [
	{
		id: 1,
		name: 'Portland'
	},
	{
		id: 2,
		name: 'San Diego'
	},
	{
		id: 3,
		name: 'Seattle'
	}
];

class SelectItemsModalKit extends React.Component {
	handleSubmit(val) {
		alert(JSON.stringify(val));
	}

	dataFn() {
		return Promise.resolve({items: ITEMS, total: 3});
	}

	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<SelectItemsModal
					{...this.props}
					dataSourceFn={this.dataFn}
					items={ITEMS}
					onSubmit={this.handleSubmit}
				/>
			</div>
		);
	}
}

export default SelectItemsModalKit;
