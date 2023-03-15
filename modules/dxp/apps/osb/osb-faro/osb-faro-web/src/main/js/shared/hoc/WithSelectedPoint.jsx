import autobind from 'autobind-decorator';
import React from 'react';
import {isNull} from 'lodash';

/**
 * HOC for tracking a selecting point.
 * @param {function} WrappedComponent
 * @returns {function} - The WrappedComponent.
 */
export default WrappedComponent => {
	class WithSelectedPoint extends React.Component {
		state = {
			selectedPoint: null
		};

		@autobind
		handlePointSelect({index}) {
			this.setState({
				selectedPoint: index
			});
		}

		render() {
			const {selectedPoint} = this.state;

			return (
				<WrappedComponent
					hasSelectedPoint={
						!isNull(selectedPoint) && isFinite(selectedPoint)
					}
					onPointSelect={this.handlePointSelect}
					selectedPoint={selectedPoint}
					{...this.props}
				/>
			);
		}
	}

	return WithSelectedPoint;
};
