import autobind from 'autobind-decorator';
import Dropdown from 'shared/components/Dropdown';
import Item from '../components/Item';
import React from 'react';
import Row from '../components/Row';

class DropdownKit extends React.Component {
	@autobind
	handleItemTwo() {
		alert('Two');
	}

	@autobind
	handleItemThree() {
		alert('Three');
	}

	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					<Dropdown label='Nested'>
						<Dropdown.Item href='#'>{'One'}</Dropdown.Item>

						<Dropdown label='more'>
							<Dropdown.Item href='#'>{'Three'}</Dropdown.Item>

							<Dropdown.Item href='#'>{'Four'}</Dropdown.Item>
						</Dropdown>

						<Dropdown.Item href='#'>{'Two'}</Dropdown.Item>
					</Dropdown>
				</Row>

				<Row>
					{Dropdown.ALIGNMENTS.map(align => (
						<Item key={align}>
							<Dropdown align={align} label={align}>
								<Dropdown.Item href='#one'>
									{'One (Link)'}
								</Dropdown.Item>
								<Dropdown.Item onClick={this.handleItemTwo}>
									{'Two (Button)'}
								</Dropdown.Item>
								<Dropdown.Item
									hideOnClick
									onClick={this.handleItemThree}
								>
									{'Three (Hide On Click)'}
								</Dropdown.Item>
							</Dropdown>
						</Item>
					))}
				</Row>
			</div>
		);
	}
}

export default DropdownKit;
