export default Component => {
	return class withRepeatableFields extends Component {
		remove() {
			this.dispatch('fieldRemoved', this.name);
		}

		repeat() {
			this.dispatch('fieldRepeated', this.name);
		}

		_handleAddRepeatedFieldButtonClicked() {
			this.repeat();
		}

		_handleRemoveRepeatedFieldButtonClicked() {
			this.remove();
		}
	};
};
