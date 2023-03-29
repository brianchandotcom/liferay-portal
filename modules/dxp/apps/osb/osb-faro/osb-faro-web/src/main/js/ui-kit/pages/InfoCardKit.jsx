import InfoCard from 'shared/components/InfoCard';
import Item from '../components/Item';
import React from 'react';

export default class InfoCardKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Item>
					<InfoCard
						header='InfoCard'
						items={[
							{name: 'name', value: 'Jim Bob Cooter'},
							{
								name: 'shippingAddress',
								value:
									'123 Fairy Lane Diamond Bar, CA 91765 USA'
							},
							{
								name: 'description',
								value:
									'Hydrofield USA is the authorized dealer for industry-leading agricultural equipment, implements, parts and service in selected areas of Texas. With a history going back to the 1800s, HOLT AgriBusiness sells and provides parts and service for the full lines of tractors from Challenger, Massey Ferguson'
							}
						]}
					/>
				</Item>
			</div>
		);
	}
}
