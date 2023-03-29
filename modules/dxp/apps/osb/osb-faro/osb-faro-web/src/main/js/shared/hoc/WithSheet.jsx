import React from 'react';
import Sheet from 'shared/components/Sheet';

/**
 * Wraps a component with a Sheet.
 * @param sheetParams - Props to be passed to the Sheet Component.
 * @returns {function} - The Sheet hoc with applied params.
 */
export default (sheetParams = {}) => WrappedComponent =>
	class extends React.Component {
		render() {
			return (
				<Sheet
					{...sheetParams}
					className={
						this.props.className ? ` ${this.props.className}` : ''
					}
				>
					<WrappedComponent {...this.props} />
				</Sheet>
			);
		}
	};
