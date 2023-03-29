import React from 'react';

/**
 * Typically used when trying to render an array of components(or <Fragment>) at
 * the top level of your application
 *
 * @example
 * class ExampleComponent extends Component {
 * 	render() {
 * 		return (
 * 			<Fragment>
 * 				<div>1</div>
 * 				<div>2</div>
 * 			</Fragment>
 * 		);
 * 	}
 * }
 *
 * wrapFragment(ExampleComponent)
 */
export default function wrapFragment(TestComponent, props) {
	class TestWithOverlay extends React.Component {
		render() {
			return (
				<div
					className={
						this.props.className ? ` ${this.props.className}` : ''
					}
				>
					<TestComponent {...props} />
				</div>
			);
		}
	}

	const component = new TestWithOverlay();

	return component.refs.child;
}
