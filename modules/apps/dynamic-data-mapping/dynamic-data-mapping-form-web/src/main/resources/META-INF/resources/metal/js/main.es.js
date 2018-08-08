import {Config} from 'metal-state';
import Builder from './pages/builder/index.es';
import ClayNavigationBar from 'clay-navigation-bar';
import Component from 'metal-jsx';
import LayoutProvider from './components/LayoutProvider/index.es';
import loader from './components/FieldsLoader/index.es';
import withAppComposer from './hocs/withAppComposer/index.es';

const LayoutProviderWithAppComposer = withAppComposer(LayoutProvider);

/**
 * Form.
 * @extends Component
 */
class Form extends Component {
	static PROPS = {
		/**
		 * The path to the SVG spritemap file containing the icons.
		 * @default undefined
		 * @instance
		 * @memberof Form
		 * @type {!string}
		 */
		spritemap: Config.string().required(),

		/**
		 * The context for rendering a layout that represents a form.
		 * @default undefined
		 * @instance
		 * @memberof Form
		 * @type {!array}
		 */
		context: Config.array().required(),
	};

	/**
	 * @inheritDoc
	 */
	render() {
		const {spritemap} = this.props;

		return (
			<div>
				<ClayNavigationBar
					items={[
						{
							active: true,
							href: '#',
							label: 'Builder',
						},
						{
							href: '#',
							label: 'Rules',
						},
					]}
					inverted={true}
					spritemap={spritemap}
				/>
				<LayoutProviderWithAppComposer {...this.props}>
					<Builder />
				</LayoutProviderWithAppComposer>
			</div>
		);
	}
}

const DDMForm = (props, container, callback) => {
	loader(
		() => {
			const instance = new Form(props, container);

			callback(instance);
		},
		props.modules,
		props.dependencies
	);
};

export default DDMForm;
export {DDMForm};
