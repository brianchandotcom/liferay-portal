<div class="columns-3" id="main-content" role="main">
	#if ($browserSniffer.isIe($request) && $browserSniffer.getMajorVersion($request) < 8)
		<table class="portlet-layout">
		<tr>
			<td class="aui-w25 portlet-column portlet-column-first" id="column-1">
				$processor.processColumn("column-1", "portlet-column-content portlet-column-content-first")
			</td>
			<td class="aui-w50 portlet-column" id="column-2">
				$processor.processColumn("column-2", "portlet-column-content")
			</td>
			<td class="aui-w25 portlet-column portlet-column-last" id="column-3">
				$processor.processColumn("column-3", "portlet-column-content portlet-column-content-last")
			</td>
		</tr>
		</table>
	#else
		<div class="portlet-layout">
			<div class="aui-w25 portlet-column portlet-column-first" id="column-1">
				$processor.processColumn("column-1", "portlet-column-content portlet-column-content-first")
			</div>

			<div class="aui-w50 portlet-column" id="column-2">
				$processor.processColumn("column-2", "portlet-column-content")
			</div>

			<div class="aui-w25 portlet-column portlet-column-last" id="column-3">
				$processor.processColumn("column-3", "portlet-column-content portlet-column-content-last")
			</div>
		</div>
	#end
</div>