## BNDMultipleAppBNDsCheck

When two modules have the same name (which happens when a module is in both the
'apps' and 'dxp/apps/' directory), only one of them is allowed to have a
`app.bnd` file, to prevent unexpected behavior when the two `app.bnd` files have
different content.