import Item from '../components/Item';
import React from 'react';
import Row from '../components/Row';
import Select from 'shared/components/Select';

class SelectKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					<Item>
						<Select>
							<Select.Item>{'one'}</Select.Item>
							<Select.Item>{'two'}</Select.Item>
							<Select.Item>{'three'}</Select.Item>
							<Select.Item>{'four'}</Select.Item>
						</Select>
					</Item>
				</Row>

				<Row>
					<Item>
						<Select showBlankOption>
							<Select.Item>{'show'}</Select.Item>
							<Select.Item>{'blank'}</Select.Item>
							<Select.Item>{'option'}</Select.Item>
						</Select>
					</Item>
				</Row>

				<Row>
					<Item>
						<Select multiple>
							<Select.Item>{'one'}</Select.Item>
							<Select.Item>{'two'}</Select.Item>
							<Select.Item>{'three'}</Select.Item>
							<Select.Item>{'four'}</Select.Item>
						</Select>
					</Item>
				</Row>
			</div>
		);
	}
}

export default SelectKit;
