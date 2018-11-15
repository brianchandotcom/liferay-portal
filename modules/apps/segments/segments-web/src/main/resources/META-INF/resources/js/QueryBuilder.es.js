import React, { Component } from "react";
import PropTypes from "prop-types";

export default class QueryBuilder extends Component {
	render() {
		return (
			<div>
				{"Hello, "} {this.props.name}
			</div>
		);
	}
}

QueryBuilder.propTypes = {
	name: PropTypes.string
};
