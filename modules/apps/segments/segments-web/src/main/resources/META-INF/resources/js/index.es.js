import React from "react";
import ReactDOM from "react-dom";
import QueryBuilder from "./QueryBuilder.es";

export default function(elementId, data) {
	console.log("data", data);

	const { segmentName } = data;

	ReactDOM.render(
		<QueryBuilder name={segmentName} />,
		document.getElementById(elementId)
	);
}
