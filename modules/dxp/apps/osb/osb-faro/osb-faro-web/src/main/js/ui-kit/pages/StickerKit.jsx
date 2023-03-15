import React from 'react';
import Row from '../components/Row';
import Sticker from 'shared/components/Sticker';

class StickerKit extends React.Component {
	render() {
		return (
			<div>
				<h4>{'Displays'}</h4>

				<Row>
					{Sticker.DISPLAYS.map((display, index) => (
						<div className='mr-1' key={index}>
							<Sticker display={display} symbol='bolt' />
						</div>
					))}
				</Row>

				<h4>{'Sizes'}</h4>

				<Row className='align-items-center'>
					{Sticker.SIZES.map((size, index) => (
						<div className='mr-1' key={index}>
							<Sticker
								display='primary'
								size={size}
								symbol='bolt'
							/>
						</div>
					))}
				</Row>
			</div>
		);
	}
}

export default StickerKit;
