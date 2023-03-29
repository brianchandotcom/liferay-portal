import React from 'react';
import Row from '../components/Row';
import TrendLine from 'shared/components/TrendLine';
import {PropTypes} from 'prop-types';
import {withSelectedPoint} from 'shared/hoc';

class TrendLineKit extends React.Component {
	static propTypes = {
		onPointSelect: PropTypes.func.isRequired,
		selectedPoint: PropTypes.number
	};

	render() {
		const {onPointSelect, selectedPoint} = this.props;

		return (
			<div>
				<h3>{`Current Point: ${selectedPoint}`}</h3>
				<Row>
					<TrendLine
						data={[4, 5, 1, 19, 5]}
						onPointSelect={onPointSelect}
					/>
				</Row>
			</div>
		);
	}
}

export default withSelectedPoint(TrendLineKit);
