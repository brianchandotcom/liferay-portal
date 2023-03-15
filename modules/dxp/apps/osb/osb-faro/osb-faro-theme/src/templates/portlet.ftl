<#assign portlet_display = portletDisplay />

${renderRequest.setAttribute("RENDER_PORTLET_BOUNDARY", false)}
${portlet_display.writeContent(writer)}