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

			fieldFocus: Config.object(),
		};

		/*
         * @param {!Object} context
         * @private
         */
		_handleShowChanged() {
			// TODO:
			// logic to autosave...

		}

		/**
		 * @inheritDoc
		 */
		render() {
			const {children} = this.props;
			// const events = {
			//     showChanged: this._handleShowChanged.bind(this),
			//     ...this.props.events
			// };

			// const props = Object.assign({...this.props}, events);

			return (
				<WrappedComponent {...this.props}>{children}</WrappedComponent>
			);
		}
	}

	return WithSidebarComposer;
};

export default withSidebarComposer;
