import Button from 'shared/components/Button';
import DatePicker from 'shared/components/date-picker';
import Overlay from 'shared/components/Overlay';
import React from 'react';
import Row from '../components/Row';

class OverlayKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					<Overlay>
						<Button>{'Hover Me'}</Button>

						<DatePicker />
					</Overlay>
				</Row>

				<Row>
					<Overlay>
						<Button>{'Nested'}</Button>

						<Overlay>
							<Button>{'Nested 2'}</Button>

							<Overlay>
								<Button>{'Hover Me'}</Button>

								<DatePicker />
							</Overlay>
						</Overlay>
					</Overlay>
				</Row>
			</div>
		);
	}
}

export default OverlayKit;
