export default Component => {
	return class withDispatch extends Component {
		dispatch(...args) {
			const {dispatch} = this.context;

			(dispatch || this.emit).apply(this, args);
		}
	};
};
