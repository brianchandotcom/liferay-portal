import Avatar from 'shared/components/Avatar';
import Item from '../components/Item';
import React from 'react';
import Row from '../components/Row';
import Sticker from 'shared/components/Sticker';
import {mockIndividual} from 'test/data';

class AvatarKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					<Item>
						<Avatar
							entity={mockIndividual(0, {
								image: 'http://i.imgur.com/G5pfP.jpg'
							})}
						/>
					</Item>

					<Item>
						<Avatar
							entity={mockIndividual(0, {
								familyName: undefined
							})}
						/>
					</Item>

					<Item>
						<Avatar entity={mockIndividual()} />
					</Item>
				</Row>

				<Row>
					{Sticker.DISPLAYS.map((item, index) => (
						<Item key={index}>
							<Avatar entity={mockIndividual(index)} />
						</Item>
					))}
				</Row>

				<Row>
					{Sticker.SIZES.map((size, index) => (
						<Item key={index}>
							<Avatar entity={mockIndividual()} size={size} />
						</Item>
					))}
				</Row>
			</div>
		);
	}
}

export default AvatarKit;
