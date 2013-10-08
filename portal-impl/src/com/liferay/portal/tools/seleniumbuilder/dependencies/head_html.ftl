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
			margin-bottom: 0px;
			padding: 0px;
		}

		li {
			display: block;
		}

		ul {
			display: block;
			list-style-type: none;
		}

		#code {
			height: 65%;
			margin: 0px;
			overflow: auto;
			padding: 0px;
			width: 100%;
		}


		#log {
			border: 1px solid #CCC;
			height: 25%;
			overflow: auto;
			width: 50%;
		}

		#log2 {
			position:fixed;
			top:50px;
			left:990px;
		}

		#log2 {
			border: 1px solid #CCC;
			height: 25%;
			overflow: auto;
			width: 45%;
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
			float: right;
			margin-left: 5px;
			margin-right: 5px;
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