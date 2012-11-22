<#--
FreeMarker Transform Template

All dynamic elements in a structure can be accessed as a FreeMarker variable.

The given structure:

  <root>
    <dynamic-element name="main-text" type="text_area">
      <dynamic-element name="sub-image" type="image" />
      <dynamic-element name="sub-text" type="text" />
    </dynamic-element>
    <dynamic-element name="more-text" type="text_area" />
    <dynamic-element name="ms-list" type="multi-list">
      <dynamic-element name="chocolate" type="Chocolate" />
      <dynamic-element name="strawberry" type="Strawberry" />
      <dynamic-element name="vanilla" type="Vanilla" />
    </dynamic-element>
  </root>

The dynamic element "main-text" can be accessed in the following ways:

  ${main-text.name}: The name "main-text"

  ${main-text.data}: The data in the article for main-text

  ${main-text.type}: The type "text-area"

  ${main-text.children}: A collection with two nodes (sub-image and sub-text)
                         that can be used in the <#list/> clause

  ${main-text.siblings}: A collection of elements with the name "main-text".
                         This will only return more than one element if this
						 element is repeatable.

One special accessor exists for elements of type "multi-list":

  ${ms-list.options}: A collection with up to three string entries (chocolate,
                      strawberry, or vanilla) that can be used in the <#list />
					  clause

Another special accessor exists for elements of type "link_to_layout":

  ${linkToPage.url}: The URL that links to the selected page in the current
                     site.

The variable ${journalTemplatesPath} can be used to include
another Journal template, e.g. <#include "${journalTemplatesPath}/LAYOUT-PARENT" />

The variable ${viewMode} specifies which mode the article is being viewed in.
For example, if ${viewMode} evaluates to "print", that means the user clicked
the print icon to view this article.
-->