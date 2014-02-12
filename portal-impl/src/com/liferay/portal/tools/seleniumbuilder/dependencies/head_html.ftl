<head>
	<script type='text/javascript'>
		function errorLogSizeCheck() {
			if (document.getElementById("errorLogSizeButton").checked) {
				document.getElementById("errorLogSize").innerHTML = "Decrease Error Log Size";

				actionCommandLog.style.height = "37%";
				actionScreenShotLog.style.height= "37%";
				descriptionLog.style.height = "37%";
				errorLog.style.height = "50%";
				pageObjectXMLLog.style.height = "37%";
			}
			else {
				document.getElementById("errorLogSize").innerHTML = "&nbsp;Increase Error Log Size";

				actionCommandLog.style.height = "75%";
				actionScreenShotLog.style.height= "75%";
				descriptionLog.style.height = "75%";
				errorLog.style.height = "12%";
				pageObjectXMLLog.style.height = "75%";
			}
		}

		function pauseButtonCheck() {
			if (document.getElementById("pauseButton").checked) {
				document.getElementById("pause").innerHTML = "Paused...";
			}
			else {
				document.getElementById("pause").innerHTML = "&nbsp;&nbsp;Pause&nbsp;&nbsp;&nbsp;";
			}
		}

		function radioCheck () {
			var actionCommandLogButtonChecked = document.getElementById("actionCommandLogButton").checked;
			var descriptionLogButtonChecked = document.getElementById("descriptionLogButton").checked;
			var disableActionScreenShotButtonChecked = document.getElementById("disableActionScreenShotButton").checked;
			var enableActionScreenShotButtonChecked = document.getElementById("enableActionScreenShotButton").checked;
			var xmlLogButtonChecked = document.getElementById("xmlLogButton").checked;

			var actionCommandLog = document.getElementById("actionCommandLog");
			var actionScreenShotLog = document.getElementById("actionScreenShotLog");
			var descriptionLog = document.getElementById("descriptionLog");
			var pageObjectXMLLog = document.getElementById("pageObjectXMLLog");

			if ((actionCommandLogButtonChecked) && (disableActionScreenShotButtonChecked)) {
				actionCommandLog.style.display = "block";
				actionCommandLog.style.width = "99%";
				descriptionLog.style.display ="none";
				actionScreenShotLog.style.display = "none";
				pageObjectXMLLog.style.display = "none";
			}
			else if ((actionCommandLogButtonChecked) && (enableActionScreenShotButtonChecked)) {
				actionCommandLog.style.display = "block";
				actionCommandLog.style.width = "46%";
				descriptionLog.style.display ="none";
				actionScreenShotLog.style.display = "block";
				pageObjectXMLLog.style.display = "none";
			}
			else if ((descriptionLogButtonChecked) && (disableActionScreenShotButtonChecked)) {
				actionCommandLog.style.display = "none";
				actionScreenShotLog.style.display = "none";
				descriptionLog.style.display ="block";
				descriptionLog.style.width = "99%";
				pageObjectXMLLog.style.display = "none";
			}
			else if ((descriptionLogButtonChecked) && (enableActionScreenShotButtonChecked)) {
				actionCommandLog.style.display = "none";
				actionScreenShotLog.style.display = "block";
				descriptionLog.style.display ="block";
				descriptionLog.style.width = "46%";
				pageObjectXMLLog.style.display = "none";
			}
			else if ((xmlLogButtonChecked) && (disableActionScreenShotButtonChecked)) {
				actionCommandLog.style.display = "none";
				actionScreenShotLog.style.display = "none";
				descriptionLog.style.display ="none";
				pageObjectXMLLog.style.width = "99%";
				pageObjectXMLLog.style.display = "block";
			}
			else if ((xmlLogButtonChecked) && (enableActionScreenShotButtonChecked)) {
				actionCommandLog.style.display = "none";
				actionScreenShotLog.style.display = "block";
				descriptionLog.style.display ="none";
				pageObjectXMLLog.style.width = "46%";
				pageObjectXMLLog.style.display = "block";
			}
		}

		function toggle(event) {
			var node;

			if (event.srcElement == undefined) {
				node = event.target;
			}
			else {
				node = event.srcElement;
			}

			var id = node.getAttribute("id");

			var firstChar = id.charAt(0);

			id = firstChar.toUpperCase() + id.slice(1);

			if (id != null) {
				if (node.innerHTML == "-") {
					node.innerHTML = "+";

					document.getElementById("collapse" + id).style.display = "none";
				}
				else if (node.innerHTML == "+") {
					node.innerHTML = "-";

					document.getElementById("collapse" + id).style.display = "block";
				}
			}
		}
	</script>

	<style>
		body {
			font-family: verdana;
			font-size: 12px;
			line-height: 1.75em;
			margin-bottom: 0px;
			padding: 0px;
		}

		input[type=checkbox] {
			display: none;
		}

		input[type=checkbox] + label {
			background-color: #e7e7e7;
			display: inline-block;
			padding: 4px 12px;
		}

		input[type=checkbox]:checked + label {
			background-color: #d0d0d0;
		}

		input[type=radio] {
			display: none;
		}

		input[type=radio] + label {
			background-color: #e7e7e7;
			display: inline-block;
			padding: 4px 12px;
		}

		input[type=radio]:checked + label {
			background-color: #d0d0d0;
		}

		li {
			display: block;
		}

		ul {
			display: block;
			list-style-type: none;
		}

		#actionCommandLog {
			border: 1px solid #CCC;
			float: left;
			height: 75%;
			overflow: auto;
			white-space: nowrap;
			width: 99%;
		}

		#actionScreenShotLog {
			border: 1px solid #CCC;
			float: left;
			height: 75%;
			overflow: auto;
			white-space: nowrap;
			width: 53%;
		}

		#descriptionLog {
			border: 1px solid #CCC;
			float: left;
			height: 75%;
			overflow: auto;
			white-space: nowrap;
			width: 99%;
		}

		#errorLog {
			border: 1px solid #CCC;
			height: 12%;
			overflow: auto;
			white-space: nowrap;
			width: 99%;
		}

		#pageObjectXMLLog {
			border: 1px solid #CCC;
			float: left;
			height: 75%;
			overflow: auto;
			white-space: nowrap;
			width: 99%;
		}

		#title {
			margin: 0px;
			max-height: 5%;
			padding: 0px;
			width: 100%;
		}

		.arrow {
			color: blue;
		}

		.attribute {
			color: purple;
		}

		.collapse {
			display: none;
		}

		.expand-line {
			cursor: pointer;
			font-weight: bold;
		}

		.expand-toggle {
			cursor: pointer;
			float: left;
			margin-right: 5px;
			width: 8px;
		}

		.fail {
			background-color: #FF8B8B;
		}

		.line-number {
			color: black;
			float: right;
			margin-left: 5px;
			margin-right: 5px;
		}

		.options {
			display: table-cell;
			min-width: 800px;
			padding-right: 10px;
			text-align: left;
		}

		.parameter-border {
			background-color: white;
			border-style: inset;
			color: darkgray;
			margin-left: 38px;
		}

		.parameter-value {
			color: black;
		}

		.pass {
			background-color: #B5FF8B;
		}

		.pending {
			background-color: #FBFF8B;
		}

		.quote {
			color: deeppink;
		}

		.skip {
			background-color: lightgray;
		}

		.tag {
			color: green;
		}
	</style>
</head>