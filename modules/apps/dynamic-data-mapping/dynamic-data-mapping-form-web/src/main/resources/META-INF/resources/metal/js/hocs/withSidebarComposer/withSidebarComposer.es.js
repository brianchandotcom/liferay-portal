import {Config} from 'metal-state';
import Component from 'metal-jsx';

/**
 * Higher-Order Component
 * @param {!Object} WrappedComponent
 * @return {!Object} new component
 */
const withSidebarComposer = WrappedComponent => {
	/**
	 * With Sidebar Composer.
	 * @extends Component
	 */
	class WithSidebarComposer extends Component {
		static PROPS = {
			fieldContext: Config.array().value([]),

			context: Config.array(),

			focusedField: Config.object(),
		};

		/*
         * @param {!Object} context
         * @private
         */
		_handleShowChanged() {}

		/**
		 * @inheritDoc
		 */
		render() {
			const {children} = this.props;

			return (
				<WrappedComponent {...this.props}>{children}</WrappedComponent>
			);
		}
	}

	return WithSidebarComposer;
};

export default withSidebarComposer;
