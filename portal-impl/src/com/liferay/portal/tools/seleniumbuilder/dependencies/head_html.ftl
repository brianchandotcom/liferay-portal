<head>
	<script type='text/javascript'>
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
		}

		li {
			display: block;
		}

		ul {
			list-style-type: none;
		}

		#log {
			border: 1px solid #CCC;
			height: 250px;
			overflow: auto;
			width: 500px;
		}

		.arrow {
			color: blue;
		}

		.attribute {
			color: purple;
		}

		.closingTag {
			display: none;
		}

		.collapse {
			display: none;
		}

		.expandLine {
			cursor: pointer;
			font-weight: bold;
		}

		.expandToggle {
			cursor: pointer;
			float: left;
			margin-right: 5px;
			width: 8px;
		}

		.fail {
			background-color: #FF8B8B;
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

		.tag {
			color: green;
		}
	</style>
</head>