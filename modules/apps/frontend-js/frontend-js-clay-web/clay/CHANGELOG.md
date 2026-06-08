# Change Log

All notable changes to Clay packages are documented in this file.

# [3.165.0] (2026-06-05)

## @clayui/badge

### Commits

- LPD-83292 Document the ClayBadge icon variant and add a Storybook story ([d6c279b](https://github.com/liferay/liferay-portal/commit/d6c279b70540fca1b26ccd1f46365689dfd59ee3))
- LPD-83292 Add a SeeAll Badge story covering all icon display types ([c6c6ac3](https://github.com/liferay/liferay-portal/commit/c6c6ac3a56e927ddc221fbd280d319f2beb4e5f8))

## @clayui/css

### Commits

- LPD-93246 SF ([55c262d](https://github.com/liferay/liferay-portal/commit/55c262d7cbe367c92a752227df9c1e7335b73323))
- LPD-93019 Use the -md border radius for inverse and bold labels ([7b7d581](https://github.com/liferay/liferay-portal/commit/7b7d581445e61dd5af25e365351df085cd8a29f3))

# [3.164.0] (2026-06-02)

## @clayui/autocomplete

### Commits

- LPD-88727 Wire KeyboardArrowsIndicator into Autocomplete ([92158ce](https://github.com/liferay/liferay-portal/commit/92158ceb82013386c9fae3d75d30ada66a28edbf))
- LPD-39987 Make KeyboardArrowsIndicator purely visual (aria-hidden) ([32442e9](https://github.com/liferay/liferay-portal/commit/32442e98739cfe6ccbba503159df55d8a61440c9))
- LPD-39987 Await userEvent in keyboard arrows indicator tests ([4759391](https://github.com/liferay/liferay-portal/commit/47593914f62fa2e0b68ba80cf26ac251a7b067a4))
- LPD-74696 Improve Enter key handling in Clay autocomplete ([1db5b27](https://github.com/liferay/liferay-portal/commit/1db5b274c0ecec12156ce7ed21af53e2619be8bc))

## @clayui/card

### Commits

- LPD-79840 Add disabled class to Clay Card component ([49cc884](https://github.com/liferay/liferay-portal/commit/49cc884d66d34b1af78fda8e6877946f11946732))
- LPD-91533 Add taller card example to ensure checkbox is centered ([082d73f](https://github.com/liferay/liferay-portal/commit/082d73f9d89a32f7af05ed1c6459210b585500f0))
- LPD-91533 Extract shared actions array in card stories ([c24dcca](https://github.com/liferay/liferay-portal/commit/c24dcca2ddf534bdd9903867675bbed232137520))

## @clayui/core

### Commits

- LPD-89790 @clayui/core use composite icons in KeyboardArrowsIndicator ([4dda27f](https://github.com/liferay/liferay-portal/commit/4dda27f21ba2877e1b6f8ca5f06ddb4f149712d5))
- LPD-89790 Move arrows-all colors from SVG to CSS for dark-mode parity ([a47512c](https://github.com/liferay/liferay-portal/commit/a47512cca39cc7ec6f1aa5682ae48b680bf8e76b))
- LPD-90345 Require aria-controls on ResizeHandle ([b1e0cc6](https://github.com/liferay/liferay-portal/commit/b1e0cc68fe834342f3ca9e69bbaa811f2becf836))
- LPD-90345 Set aria-label by default ([758cce6](https://github.com/liferay/liferay-portal/commit/758cce6ca7833cd7c537382822b7cb311a8fa644))
- LPD-90345 Set aria-valuetext by default ([f7fb48b](https://github.com/liferay/liferay-portal/commit/f7fb48be1d1788443be25e55161b7fffd3746f4a))
- LPD-90345 Adapt the documentation ([2b71242](https://github.com/liferay/liferay-portal/commit/2b71242cc5b10985ea92e21e3fbafa762cea7fc4))
- LPD-90345 Prevent className override on ResizeHandle ([a294a28](https://github.com/liferay/liferay-portal/commit/a294a281c6fd99d670a4e56d1651e3a3932b861b))
- LPD-90345 Resolve SidePanel id in render for ResizeHandle aria-controls ([a7e7dcb](https://github.com/liferay/liferay-portal/commit/a7e7dcb322c99859568fb47ea902c4e38497d075))
- LPD-90345 Adapt snapshots ([f9f979b](https://github.com/liferay/liferay-portal/commit/f9f979b2b80ced0f3e7afd2f921f1c720b23f3d1))
- LPD-39987 Add tooltip-style anchored placement to KeyboardArrowsIndicator ([847d600](https://github.com/liferay/liferay-portal/commit/847d600a9dcd49d999a0fcbd575a101ec4e5cfa5))
- LPD-88716 Wire KeyboardArrowsIndicator into Picker ([74ae0fa](https://github.com/liferay/liferay-portal/commit/74ae0facfbd51441c5642e73921d1f0fbf30edad))
- LPD-88725 Wire KeyboardArrowsIndicator into IconSelector ([af0ef9c](https://github.com/liferay/liferay-portal/commit/af0ef9c7ef687343905500acc371079042684409))
- LPD-88730 Wire KeyboardArrowsIndicator into VerticalBar ([134f2d2](https://github.com/liferay/liferay-portal/commit/134f2d2472848c983a41ef80dcba663c4e8f06a1))
- LPD-88732 Wire KeyboardArrowsIndicator into VerticalNav ([2ca6383](https://github.com/liferay/liferay-portal/commit/2ca638379dfea77325dffff596ff3b82c2df80d7))
- LPD-91699 Wire KeyboardArrowsIndicator into ResizeHandle ([2f6f6a0](https://github.com/liferay/liferay-portal/commit/2f6f6a02c0a977910fc11107454a69788f7842bc))
- LPD-39987 Make KeyboardArrowsIndicator purely visual (aria-hidden) ([32442e9](https://github.com/liferay/liferay-portal/commit/32442e98739cfe6ccbba503159df55d8a61440c9))
- LPD-39987 Scope the indicator's focus listener to the anchor element ([c932951](https://github.com/liferay/liferay-portal/commit/c93295144e73643c45b3979db01fb9829fa0e06d))
- LPD-88739 Wire KeyboardArrowsIndicator into Table ([f8165ec](https://github.com/liferay/liferay-portal/commit/f8165ece71a331f89f4a794cb25581382345a316))
- LPD-39987 Add Center alignment and tooltip-top indicator placement ([d89b5a9](https://github.com/liferay/liferay-portal/commit/d89b5a9b126bcd6da58e2fc0c6ac91eefa26534a))
- LPD-39987 Await userEvent in keyboard arrows indicator tests ([4759391](https://github.com/liferay/liferay-portal/commit/47593914f62fa2e0b68ba80cf26ac251a7b067a4))
- LPD-39987 Fix Picker indicator assertion when no img role is in the DOM ([52c62b9](https://github.com/liferay/liferay-portal/commit/52c62b9c2b7952b5c511266ffcc84696d38f7ad2))
- LPD-39987 Mock fetch per-test in IconSelector indicator suite ([bdeccb7](https://github.com/liferay/liferay-portal/commit/bdeccb704d6155666f13d4c1c5ddad80dee3dba3))
- LPD-39987 Query portaled indicator from document.body in VerticalNav tests ([732245c](https://github.com/liferay/liferay-portal/commit/732245c359646a0ef729f57693c477c14a0f15d6))
- LPD-31376 TreeViewGroup switched from implicit findDOMNode lookup to the explicit nodeRef opt-in ([0c44546](https://github.com/liferay/liferay-portal/commit/0c445468eacf46799621134ae04d5523d8bae9b5))
- LPD-31376 Refactor functions declarations ([944d8ef](https://github.com/liferay/liferay-portal/commit/944d8efa224dcee27fa34128009a033403774850))

## @clayui/css

### Commits

- LPD-89790 @clayui/css replace arrow-key-* with arrows-* icons ([a273124](https://github.com/liferay/liferay-portal/commit/a27312488a14c74f3f274d72fac4047d4b69b721))
- LPD-89790 SF ([5185c59](https://github.com/liferay/liferay-portal/commit/5185c590694d52031b7f0b6a24feb7f973da8eb6))
- LPD-89790 Move arrows-all colors from SVG to CSS for dark-mode parity ([a47512c](https://github.com/liferay/liferay-portal/commit/a47512cca39cc7ec6f1aa5682ae48b680bf8e76b))
- LPD-89790 SF ([5f18e93](https://github.com/liferay/liferay-portal/commit/5f18e93f9704669f24602eb74c8780e6f73ef6bc))
- LPD-89790 Move lexicon-icon size utilities below .clay-keyboard-arrows-indicator ([0ef588f](https://github.com/liferay/liferay-portal/commit/0ef588fbd82e18f7548280d47363ed72e2a68b28))
- LPD-89790 Wrap hsl(from ...) in unquote for liferay-theme-tasks Sass ([5c657ab](https://github.com/liferay/liferay-portal/commit/5c657abc52cbcd18cd5dcbfaa5eae6296a043362))
- LPD-79840 Apply quick-win visual improvements to the existing Clay Card component ([7730ec2](https://github.com/liferay/liferay-portal/commit/7730ec20b16f9ba33c5f96d9f70f2d7821138597))
- LPD-79840 Add disabled class to Clay Card component ([49cc884](https://github.com/liferay/liferay-portal/commit/49cc884d66d34b1af78fda8e6877946f11946732))
- LPD-89211 Exclude .ck from being affected by .cadmin CSS ([a4c64a0](https://github.com/liferay/liferay-portal/commit/a4c64a04fe11c6e64a5070dc648e6a87e0b81732))
- LPD-88103 Update Clay label border-radius and text-transform ([1fa5890](https://github.com/liferay/liferay-portal/commit/1fa5890051a9c239bef0b3d80a0a39cb1d89bd4f))
- LPD-88103 Restyle Clay label colors and font sizes ([4ec5e0c](https://github.com/liferay/liferay-portal/commit/4ec5e0ce7cc673496a90a687ae786565bb3d1d36))
- LPD-88103 Apply color-d2 level to the text of Clay inverse labels ([de07090](https://github.com/liferay/liferay-portal/commit/de07090b1a42ac826d0ccfa48187c931e8fb415c))
- LPD-88103 Prevent unexpected changes for existing themes that extend styled by default ([328fe19](https://github.com/liferay/liferay-portal/commit/328fe19f8949aad0535898da0f9cf01472182b36))
- LPD-88103 Fix Atlas colors after removing from base ([7ae219a](https://github.com/liferay/liferay-portal/commit/7ae219a115b6bab7897ffcd75d11c6363ec8afac))
- LPD-90345 Add active and focus-visible styles to the resizer ([b08a245](https://github.com/liferay/liferay-portal/commit/b08a2450003323c3e093c7d9b7950eb84b889998))
- LPD-90345 Preserve horizontal resizer placement under c-prefers-focus-ring ([36ac1eb](https://github.com/liferay/liferay-portal/commit/36ac1eb27cf6fa8ef4e8b18b31b5948f308a161c))
- LPD-91067 Selected state of Cards not applying the primary stroke correctly ([8e8ccc7](https://github.com/liferay/liferay-portal/commit/8e8ccc7e27c077410144ed4e2b8c680110b62cc7))
- LPD-91067 Remove form-check-card when it's disabled ([0171777](https://github.com/liferay/liferay-portal/commit/017177766d886f1b0b22684bc03e22b9c86d7dc9))
- LPD-90973 Add Content Label variants to Clay ([69271a3](https://github.com/liferay/liferay-portal/commit/69271a3795e2ae19949251d0da3d6eb2324c0019))
- LPD-90973 Make border transparent for inverse ClayLabel component ([6a7f2cd](https://github.com/liferay/liferay-portal/commit/6a7f2cd65122211a81e855d8952fc7e0847b7e5e))
- LPD-39987 Add tooltip-style anchored placement to KeyboardArrowsIndicator ([847d600](https://github.com/liferay/liferay-portal/commit/847d600a9dcd49d999a0fcbd575a101ec4e5cfa5))
- LPD-92126 Decouple tabindex and type label styling from href ([33387a9](https://github.com/liferay/liferay-portal/commit/33387a9056ac7959b395ea6101eea40e9922ccaf))
- LPD-87424 Set $mark-bg to $yellow-l3 across atlas and cadmin ([fd36852](https://github.com/liferay/liferay-portal/commit/fd36852a3d33be059ccef03d13f0098e9670323c))
- LPD-92129 This rule should apply only to Safari and be avoided in any other browser ([67b2e5b](https://github.com/liferay/liferay-portal/commit/67b2e5b6884f2dc862fcab39e7c82ca10d0c4ba8))
- LPD-91533 Checkbox and Radio buttons should be vertically centered in horizontal cards ([9d2fe7f](https://github.com/liferay/liferay-portal/commit/9d2fe7f6d523b1859f3ddab398d32befc1399b24))
- LPD-92478 Use secondary-l2 for Clay Table borders ([c56359f](https://github.com/liferay/liferay-portal/commit/c56359f7ac49d8f76c60031da2bd167ab6f21068))
- LPD-92478 Use gray-400 for Clay Table borders ([482b12d](https://github.com/liferay/liferay-portal/commit/482b12d15a57598f04e39285293d0d2ce9a05c9c))
- LPD-90766 @clayui/css add Analytics Cloud and chart icons ([15745da](https://github.com/liferay/liferay-portal/commit/15745dad9b080cd95c5e604fdc5427d9f40b91c1))
- LPD-90766 SF ([e5fcc00](https://github.com/liferay/liferay-portal/commit/e5fcc00c05147e56e5cb82f81ab8e083b057f149))
- LPD-85958 Convert Cadmin to use CSS custom properties and add color schemes for dark/light mode ([22d2180](https://github.com/liferay/liferay-portal/commit/22d218049cd912fb7b6b5f87fc9cbdbce167a93c))
- LPD-85958 Atlas Custom Properties adds light, dark, dark-high-contrast color schemes     - Use `unquote('hsl()')` pattern to prevent Sass from using Sass color functions instead of CSS color functions     - Source Formatting ([52b1f2d](https://github.com/liferay/liferay-portal/commit/52b1f2d747499294b017905244a8c55a330b0c69))
- LPD-85958 Clay Atlas / Base convert hardcoded colors to use global color variables ([3b1a916](https://github.com/liferay/liferay-portal/commit/3b1a91689764c2932286337cbc75b34c43751b89))
- LPD-85958 Update Clay dark and dark high contrast to newer values ([b2f2bbb](https://github.com/liferay/liferay-portal/commit/b2f2bbb4e4e066ac892fe0f1966053e327566673))

## @clayui/date-picker

### Commits

- LPD-91601 Support min and max in ClayDatePicker ([beaeb2e](https://github.com/liferay/liferay-portal/commit/beaeb2e5120791b39b77fd8dd8b08df8e44264b5))
- LPD-91601 Use aria-disabled for out-of-range days ([4350906](https://github.com/liferay/liferay-portal/commit/435090652097b708620dac46b1e5ae0e28a88ab9))
- LPD-91601 Announce out-of-range rejections to assistive technology ([8fe1760](https://github.com/liferay/liferay-portal/commit/8fe1760bec5e047a697881052f22e7df2d0d31d8))
- LPD-91601 Clone returned date in clamp to avoid mutation aliasing ([056ce31](https://github.com/liferay/liferay-portal/commit/056ce317bd4d93c23c0361dc8c6fbc21dcd1c38e))
- LPD-91601 Route disabled-day clicks through the parent guard ([8e04396](https://github.com/liferay/liferay-portal/commit/8e043967ee6b4b90aeb8df5c985ad4f1548aa912))
- LPD-91601 Tighten min/max tests with userEvent and jest-dom matchers ([e33ad92](https://github.com/liferay/liferay-portal/commit/e33ad928d81b5a65fdb2804b9d0b0d650bfc91f8))
- LPD-91601 Replace classList.toContain with toHaveClass in date-picker tests ([aab1bb6](https://github.com/liferay/liferay-portal/commit/aab1bb69312aa22ba0a9c98a80a1ce6d30a3e9ec))
- LPD-91601 Narrow filter predicate to keep forEach callback typed as string ([b3d7a9d](https://github.com/liferay/liferay-portal/commit/b3d7a9d43f35d7af9dd6592fa12575d83a54cf84))
- LPD-91601 Type the input variable as HTMLInputElement in min/max tests ([42f73a0](https://github.com/liferay/liferay-portal/commit/42f73a075413a039ff58e1480168c998684a15a0))
- LPD-88738 Wire KeyboardArrowsIndicator into DatePicker ([8926099](https://github.com/liferay/liferay-portal/commit/8926099961144f406799bd55e0dc5db694eba369))
- LPD-39987 Make KeyboardArrowsIndicator purely visual (aria-hidden) ([32442e9](https://github.com/liferay/liferay-portal/commit/32442e98739cfe6ccbba503159df55d8a61440c9))

## @clayui/drop-down

### Commits

- LPD-44313 Forward other props on ClayDropDownWithItems ([f961f21](https://github.com/liferay/liferay-portal/commit/f961f2150ee61a0e3ee1943cccac2ccef1e584b2))
- LPD-44313 Forward other props on ClayDropDownWithDrilldown ([cd67bd9](https://github.com/liferay/liferay-portal/commit/cd67bd97cc3152ab2e8cdd737d1e3d9431dea1b3))
- LPD-44313 Extract shared DropDownHTMLAttributes type ([992cc26](https://github.com/liferay/liferay-portal/commit/992cc2625eaf8f053d8fdbe7c802f9a94701e945))
- LPD-44313 Stop spreading messages prop into nested DropDown JSX ([1d447cd](https://github.com/liferay/liferay-portal/commit/1d447cd0a529975a55fc29aea8d6b75fd91c3bd9))
- LPD-88711 Wire KeyboardArrowsIndicator into DropDown ([2e63df8](https://github.com/liferay/liferay-portal/commit/2e63df85ecb58fe2b87933419c5f4b48bccdf5de))
- LPD-39987 Make KeyboardArrowsIndicator purely visual (aria-hidden) ([32442e9](https://github.com/liferay/liferay-portal/commit/32442e98739cfe6ccbba503159df55d8a61440c9))
- LPD-34943 Keep parent overlay accessible when nested overlay opens ([2f70757](https://github.com/liferay/liferay-portal/commit/2f7075735c83bd653bb1ea7650c5c9143cfef2ff))
- LPD-34943 Tighten overlay accessibility test and Overlay filter ([40bc7a8](https://github.com/liferay/liferay-portal/commit/40bc7a8c40d5aeae2dba0c1da89f1c36e24486b2))

## @clayui/icon

### Commits

- LPD-89790 clayui.com replace arrow-key-* with arrows-* and aliases ([b281edd](https://github.com/liferay/liferay-portal/commit/b281edd325a06a39e80cb5cf3c958036acc5ec81))
- LPD-90766 clayui.com add aliases for Analytics Cloud and chart icons ([b350410](https://github.com/liferay/liferay-portal/commit/b350410d75ecef5286745f94414d9bc8f21e9d31))

## @clayui/label

### Commits

- LPD-88103 Add inverse variant to the Clay Label component ([dc876ae](https://github.com/liferay/liferay-portal/commit/dc876ae88674304b023d8c78dab56f1e096fb7c0))
- LPD-88103 Restyle Clay label colors and font sizes ([4ec5e0c](https://github.com/liferay/liferay-portal/commit/4ec5e0ce7cc673496a90a687ae786565bb3d1d36))
- LPD-88103 Prevent inverse for unstyled display type ([979adc7](https://github.com/liferay/liferay-portal/commit/979adc7c1d2a2d02b7ae817b41c7493e5b2b6e05))
- LPD-88103 Refactor css classes assignment ([a4e1598](https://github.com/liferay/liferay-portal/commit/a4e159853165e84a515409914d5cb83b2568fcb8))
- LPD-90973 Add Content Label variants to Clay ([69271a3](https://github.com/liferay/liferay-portal/commit/69271a3795e2ae19949251d0da3d6eb2324c0019))
- LPD-90973 Add ContentLabel React component ([17ddab9](https://github.com/liferay/liferay-portal/commit/17ddab94c90c6d08ee363d80a3f7ab299b0233af))
- LPD-90973 Make displayType only optional for ClayLabel component ([460e4ff](https://github.com/liferay/liferay-portal/commit/460e4ff6a66079adc1dc19624fcb3ebd83b570b1))
- LPD-90973 Split Default and SeeAll stories for ClayLabel component ([ee04483](https://github.com/liferay/liferay-portal/commit/ee044832d7448770dd980ca907334c6bfd1e47e6))

## @clayui/multi-select

### Commits

- LPD-88735 Wire KeyboardArrowsIndicator into MultiSelect ([cd1ac5c](https://github.com/liferay/liferay-portal/commit/cd1ac5cf55019889705d9e01f584597cb7123c10))
- LPD-39987 Make KeyboardArrowsIndicator purely visual (aria-hidden) ([32442e9](https://github.com/liferay/liferay-portal/commit/32442e98739cfe6ccbba503159df55d8a61440c9))
- LPD-39987 SF ([8d4739e](https://github.com/liferay/liferay-portal/commit/8d4739e95a07b6065ea3970837b40c52bc3b0f1e))
- LPD-39987 Move MultiSelect indicator portal outside Container ([7f7918a](https://github.com/liferay/liferay-portal/commit/7f7918ab30ce3b2e64205257497e7cb0a912fea9))
- LPD-39987 Await userEvent in keyboard arrows indicator tests ([4759391](https://github.com/liferay/liferay-portal/commit/47593914f62fa2e0b68ba80cf26ac251a7b067a4))
- LPD-74696 Improve Enter key handling in Clay autocomplete ([1db5b27](https://github.com/liferay/liferay-portal/commit/1db5b274c0ecec12156ce7ed21af53e2619be8bc))
- LPD-74696 Add an allowsCustomLabel story to the MultiSelect storybook ([858ae94](https://github.com/liferay/liferay-portal/commit/858ae944333e08abceb041524e9e86d52bb24f61))

## @clayui/shared

### Commits

- LPD-39987 Add Center alignment and tooltip-top indicator placement ([d89b5a9](https://github.com/liferay/liferay-portal/commit/d89b5a9b126bcd6da58e2fc0c6ac91eefa26534a))
- LPD-34943 Keep parent overlay accessible when nested overlay opens ([2f70757](https://github.com/liferay/liferay-portal/commit/2f7075735c83bd653bb1ea7650c5c9143cfef2ff))
- LPD-34943 Tighten overlay accessibility test and Overlay filter ([40bc7a8](https://github.com/liferay/liferay-portal/commit/40bc7a8c40d5aeae2dba0c1da89f1c36e24486b2))

# [3.163.0] (2026-05-13)

## @clayui/alert

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-81627 Allow passing classNames to the container ([1f9f4da](https://github.com/liferay/liferay-portal/commit/1f9f4da502edcfdd557a19645e383624cf9bb1fc))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/autocomplete

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-82600 Fix Escape key input ([e02bcac](https://github.com/liferay/liferay-portal/commit/e02bcac12cbca7dd8d379f2b6a1ab81928aab56b))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/badge

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-83029 Allow passing icon to badge ([26ceeb3](https://github.com/liferay/liferay-portal/commit/26ceeb3731afc917d0621aea45d967f16d2df0e5))
- LPD-83029 Add test ([ffa79b5](https://github.com/liferay/liferay-portal/commit/ffa79b5ed6336425bf6a5e9e06574a78ece42b58))
- LPD-83029 Implement ItemAfter, ItemBefore and ItemExpand logic ([19fcf1f](https://github.com/liferay/liferay-portal/commit/19fcf1f41c0cbb34cefb00bcd51f9cfcce7722b1))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/breadcrumb

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/button

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/card

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-82700 Conditionally add aria-controls attribute to ClayDropDown trigger, so it won't reference a element that isn't present in the DOM ([0b476ba](https://github.com/liferay/liferay-portal/commit/0b476bac00fa0482cc266f2f7ff4628213333350))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-83803 Make ClayCardWithInfo img not draggable ([e6b9674](https://github.com/liferay/liferay-portal/commit/e6b9674a2d9f3ed65f2173fae2c7b0f00ed27940))
- LPD-83803 Update test snapshots ([208ed9b](https://github.com/liferay/liferay-portal/commit/208ed9bde0b51d3e2f1e0b84331394944c607130))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/color-picker

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-82601 Remove skip, test is working fine ([e0ad6fe](https://github.com/liferay/liferay-portal/commit/e0ad6fee201ce87c88db7b00453a265ee9c35785))
- LPD-80236 Convert index.ts into barrel file for exports ([ca8603a](https://github.com/liferay/liferay-portal/commit/ca8603a4049edfe93d16ad4b4dfa618362e35030))
- LPD-80236 Add useColorPicker hook ([82a441e](https://github.com/liferay/liferay-portal/commit/82a441e1e9a3969ae9e09d19e46607e304707916))
- LPD-80236 Add Field component to isolate the color input, splotch and dropdown ([74de4f2](https://github.com/liferay/liferay-portal/commit/74de4f237c92208742c97a8d25aae7157cb40375))
- LPD-80236 Export useColorPicker hook, Field and Editor components to reuse them ([2b55a42](https://github.com/liferay/liferay-portal/commit/2b55a42394e109edf4e454a4ea8e1cffd01cbe39))
- LPD-80236 Add the ability to add an external trigger reference ([13892d2](https://github.com/liferay/liferay-portal/commit/13892d25edd143ec7204c5c96b6870058ea9993a))
- LPD-80236 Adapt the parseColorValue function to the ClayColorPicker, taking into account the hex6 and hex8 ([c9d85e9](https://github.com/liferay/liferay-portal/commit/c9d85e96be51906c6c8699d95936255486f17418))
- LPD-80236 Save the value when onBlur is done in the Editor hex input ([ab85832](https://github.com/liferay/liferay-portal/commit/ab85832fee860e17e1a6df45b397987055a23059))
- LPD-80236 Improve props and descriptions ([6da7abb](https://github.com/liferay/liferay-portal/commit/6da7abb3a3dc9f6afcbf4eb24febbe592d4af6d9))
- LPD-80236 Rename onClickSplotch to onSplotchClick ([bec76ac](https://github.com/liferay/liferay-portal/commit/bec76acc04c3bbd9ed357adf790094fde7f09e29))
- LPD-80236 Create new function to format the hex colors ([bed726a](https://github.com/liferay/liferay-portal/commit/bed726a2fb3ce44c76a4157ac4adb2c93a77de40))
- LPD-83988 Export all the necessary functions from util.ts ([fdd7b2c](https://github.com/liferay/liferay-portal/commit/fdd7b2c82fcd839f34d5f5f7f29bd27b827c74c4))
- LPD-83988 Update the package types in the color-picker docs ([d2b0084](https://github.com/liferay/liferay-portal/commit/d2b00844c744b7175051336914471d4dd3d19957))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/core

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-78752 Allow configuring custom classes in Clay SidePanel.Title ([5626239](https://github.com/liferay/liferay-portal/commit/56262393602378d933b9d7962fc609700b4e8407))
- LPD-79027 Use existing onEnd callback ([d6d039d](https://github.com/liferay/liferay-portal/commit/d6d039d7073cbc6558ca4ab14bc08eb372c035bb))
- LPD-79027 Reset state also after drop with mouse ([03de461](https://github.com/liferay/liferay-portal/commit/03de46132d6ac765df42adc6452da3ab3280a493))
- LPD-78996 @clayui/nav VerticalNav should allow stacked navs with no indent ([d824c08](https://github.com/liferay/liferay-portal/commit/d824c08a994b3babfd22902dcb739a9f0ed11e26))
- LPD-78996 @clayui/core VerticalNav add example without indent ([60adeec](https://github.com/liferay/liferay-portal/commit/60adeec5ac228a6ae1e7a39042c3770a6c0c9a9d))
- LPD-79543 Allow side panel to disable the escape key behavior ([1aa3734](https://github.com/liferay/liferay-portal/commit/1aa3734ee319c84899b114eef93587bdf0fd9658))
- LPD-66630 Update size to small ([096b025](https://github.com/liferay/liferay-portal/commit/096b02530c74447b42f14aa7383aeef733559b6f))
- LPD-66630 Update snapshots ([4f5e77b](https://github.com/liferay/liferay-portal/commit/4f5e77b42757bc9a0d4df11a6a6826156bfaa976))
- LPD-79219 @clayui/core Treeview drag handler should be 24px x 24px when focused ([2cc084a](https://github.com/liferay/liferay-portal/commit/2cc084ae2a760b05df962eff3aff1e9b920383ba))
- LPD-75776 Fix typo ([9d38ea9](https://github.com/liferay/liferay-portal/commit/9d38ea9f9a3745e95d519a66ece726ad13fcf40d))
- LPD-81627 Make symbol optional ([2343e95](https://github.com/liferay/liferay-portal/commit/2343e95f6dc7d6d4f0d3fd6b2d4b1220b2002291))
- LPD-82602 Fix keyboard events ([cde7716](https://github.com/liferay/liferay-portal/commit/cde7716b174daa483aa7724e28846ccc94eb246c))
- LPD-82700 Conditionally add aria-controls attribute to ClayDropDown trigger, so it won't reference a element that isn't present in the DOM ([0b476ba](https://github.com/liferay/liferay-portal/commit/0b476bac00fa0482cc266f2f7ff4628213333350))
- LPD-82603 Fix escape key input in skipped side panel test ([e0029ee](https://github.com/liferay/liferay-portal/commit/e0029eec3a6084e0a952a4f4e5f7c1fb66e59e21))
- LPD-80239 Add FocusTrap to the dropdown menu ([0a906b5](https://github.com/liferay/liferay-portal/commit/0a906b542b5475edf2e05f4ed04eaf61dd23967b))
- LPD-85711 Add opt-in search capability to the Picker component ([f14c1f9](https://github.com/liferay/liferay-portal/commit/f14c1f94459d7a0876c7a5dfa51bf8df6b328daf))
- LPD-85711 Add test coverage to Picker search capability ([8201b1e](https://github.com/liferay/liferay-portal/commit/8201b1e67ea5acfd5ce9d292a022ec518b041d79))
- LPD-85711 Update Picker documentation to include the new search capability ([d465000](https://github.com/liferay/liferay-portal/commit/d4650005e444fb892fd2af4dfa1bab4ae9634e73))
- LPD-85711 Refactor Picker component to modularize search logic ([4c7178e](https://github.com/liferay/liferay-portal/commit/4c7178e991a4abe6dd5b843f3f80f23bf6ec8ef3))
- LPD-85711 Improve search accessibility ([a8a06e0](https://github.com/liferay/liferay-portal/commit/a8a06e0f6a5a61196a6dec4ead16716f5d423720))
- LPD-85711 Reorganize props ([1fca9cd](https://github.com/liferay/liferay-portal/commit/1fca9cd03e891910f79f92a26491d3f2ce42c242))
- LPD-84613 Move the usePanelWidthMax hook to clay-shared and rename it to useObservedMaxWidth ([6f6c2a7](https://github.com/liferay/liferay-portal/commit/6f6c2a771008635ed912dda53edf72240fdae6aa))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-85564 Add onItemActivate prop and default Enter behavior ([103eb11](https://github.com/liferay/liferay-portal/commit/103eb11ddbad83d7d93cb17cb72eb282ac0bfe2c))
- LPD-85564 Add tests ([c7be8b1](https://github.com/liferay/liferay-portal/commit/c7be8b1e18a5dfca19d8a8969af2a9aaf4b24242))
- LPD-38804 Add type=button to ClayPicker options ([cb6c140](https://github.com/liferay/liferay-portal/commit/cb6c14083e24ed07eb3170a85a278175959cd2cf))
- LPD-38804 Keep selected item label during search ([25a0b52](https://github.com/liferay/liferay-portal/commit/25a0b52c4ca1685de603750adb462eb09d4bc3cc))
- LPD-74518 Scroll the selected option into view when opening Picker ([de93377](https://github.com/liferay/liferay-portal/commit/de93377f714692bffb044553db021496d8e07901))
- LPD-74518 Compute scroll offset via getBoundingClientRect ([380ba94](https://github.com/liferay/liferay-portal/commit/380ba94749e53ce55c082ce1caea03820713c3e4))
- LPD-74518 Scope item lookup to listRef and simplify key fallback ([be03e9f](https://github.com/liferay/liferay-portal/commit/be03e9fe3a4ced90c458b3c741ec8d3f0c80690b))
- LPD-40038 @clayui/core add KeyboardArrowsIndicator ([79010f2](https://github.com/liferay/liferay-portal/commit/79010f2ec8d9e13f092e088cd161b37c32892a06))
- LPD-88290 Move PanelResizer to clay-core ([ddde603](https://github.com/liferay/liferay-portal/commit/ddde603ae4e42d51ceb23cbbd8a117f42e0662cc))
- LPD-88290 Rename PanelResizer by ResizeHandle ([d2453ea](https://github.com/liferay/liferay-portal/commit/d2453ea7bf7321cdcf04b9681cca1ca2447074d0))
- LPD-88290 Adapt imports ([04a0ea2](https://github.com/liferay/liferay-portal/commit/04a0ea2f7b0fa654e3ffe6cdc1c602dac1ab897b))
- LPD-88290 Rename component props and decouple from dependent component ([6636802](https://github.com/liferay/liferay-portal/commit/66368028e373ba1ace49ec8bc036a0856bc46f7d))
- LPD-88290 New styles for the resize handle ([c66e7cb](https://github.com/liferay/liferay-portal/commit/c66e7cbfd58c4a5b0b911d8b8fdfdaf84e579861))
- LPD-88747 Add storybook ([c34b520](https://github.com/liferay/liferay-portal/commit/c34b52058d1d38b409d2ca1c37d724412dd231ca))
- LPD-88746 Add documentation ([c1d7c3e](https://github.com/liferay/liferay-portal/commit/c1d7c3ea984d714823126abe9a119f1847e24112))
- LPD-88290 Add RTL compatibility ([af2828c](https://github.com/liferay/liferay-portal/commit/af2828cb9f4f6b354a34110e1664623f5e3bf1cf))
- LPD-87534 Fixed overlapping items on first open of virtualized tags dropdown ([2b1b71e](https://github.com/liferay/liferay-portal/commit/2b1b71e2d2e53b00bed03ebffe611cc0b5fc8e36))
- LPD-87534 Added Tests ([a822a0f](https://github.com/liferay/liferay-portal/commit/a822a0f12d1d3db26cb200e918905bf1575017ab))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/css

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-75997 @clayui/css Update product-menu-open.svg ([1db5459](https://github.com/liferay/liferay-portal/commit/1db545935c65cc6ca37ae37b57f27951d42405f6))
- LPD-79073 Control Menu button icons should be 16px ([36a0f8a](https://github.com/liferay/liferay-portal/commit/36a0f8adaba142c08d1ac8c460ed919ee81ff9c9))
- LPD-77928 @clayui/css SF variable files ([1fe4e68](https://github.com/liferay/liferay-portal/commit/1fe4e6817515190e3f41d435bc62f3436ba59c0d))
- LPD-79018 @clayui/css sticker-xxl icon should be 40px ([2fd0409](https://github.com/liferay/liferay-portal/commit/2fd0409656bdda0f9e2f765891de2a45660e4237))
- LPD-80362 @clayui/core Treeview icon angle-down should display larger ([22eb0b3](https://github.com/liferay/liferay-portal/commit/22eb0b3c7b87daa3ee9d61538dad07303d844537))
- LPD-78750 @clayui/css adds rocket.svg ([340f130](https://github.com/liferay/liferay-portal/commit/340f13097d6f9f3884532d483edaf83b5c6dd3f3))
- LPD-79219 @clayui/core Treeview drag handler should be 24px x 24px when focused ([2cc084a](https://github.com/liferay/liferay-portal/commit/2cc084ae2a760b05df962eff3aff1e9b920383ba))
- LPD-80374 @clayui/css add icons calculator, price-list, product-configuration, and products ([cffbeff](https://github.com/liferay/liferay-portal/commit/cffbeff59e3509c2b132969f49d4d5ce1898c232))
- LPD-80374 SF icons.svg ([c27b09c](https://github.com/liferay/liferay-portal/commit/c27b09c03777ee017fe66d7ebd5a93e66ff4e3fc))
- LPD-77810 @clayui/css Replace Sass math with CSS calc ([75730ce](https://github.com/liferay/liferay-portal/commit/75730ce27d3621b0c73c5256be4568891d44eec8))
- LPD-77810 @clayui/css Replace math-sign with CSS calc ([42a8656](https://github.com/liferay/liferay-portal/commit/42a865608c05a36c2d2fd5b313b733a8d0709726))
- LPD-77810 @clayui/css SF ([4eb1545](https://github.com/liferay/liferay-portal/commit/4eb154584aeb8bdbd82038223b5a719b81769282))
- LPD-80439 @clayui/css add layout-new-window.svg ([2310a7f](https://github.com/liferay/liferay-portal/commit/2310a7f5800515f39300f77362ccf8415d202c0f))
- LPD-79381 Scope cursor and hover effects to interactive elements ([8ae76f2](https://github.com/liferay/liferay-portal/commit/8ae76f2912116397bdad8bade5e7c8e6faa7c26c))
- LPD-81799 - Regen ([e577af8](https://github.com/liferay/liferay-portal/commit/e577af8942abc4abfc9811ba91781239cf08ef6d))
- LPD-81799 - SF ([3a6f4e5](https://github.com/liferay/liferay-portal/commit/3a6f4e5c95e8e68de19e2902eb8b673311a241c2))
- LPD-80549 @clayui/css clay-css mixin should support any CSS property ([49fc8b8](https://github.com/liferay/liferay-portal/commit/49fc8b8ad1a059fad8facc1f5e164c1373466270))
- LPD-81799 clayui.com should use local version of icons.svg in dev mode ([d57b27c](https://github.com/liferay/liferay-portal/commit/d57b27cec0cf0098114a233eced849776d0d9cd5))
- LPD-81820 @clayui/css add unit to 0 values to make it work with calc() ([9a5ffbf](https://github.com/liferay/liferay-portal/commit/9a5ffbffbb75908bc23dae70b177bcf434507fab))
- LPD-82273 @clayui/css remove use of Sass nth function with variables that could contain a CSS variable ([503c60e](https://github.com/liferay/liferay-portal/commit/503c60e225c5a2b223c7476fc007325d86172433))
- LPD-77641 @clayui/css $custom-control-indicator-checked-color should propagate to checkbox and radio ([02fe938](https://github.com/liferay/liferay-portal/commit/02fe938e328af2c1a472d06ae8ab20d55d018242))
- LPD-83068 @clayui/css add books-brush.svg ([3d3f1b2](https://github.com/liferay/liferay-portal/commit/3d3f1b221cf72464d7f28ec9ea0c907c8775cf31))
- LPD-71471 @clayui/css clay-data-label-text-position function shouldn't error with CSS variables ([a88e120](https://github.com/liferay/liferay-portal/commit/a88e1203878e72258f746b31633cfd03b3666090))
- LPD-71471 @clayui/css Use calc for $spacers ([3a8d947](https://github.com/liferay/liferay-portal/commit/3a8d9476d2c67482bc535ebf16aad92fa46f9f7a))
- LPD-71471 @clayui/css Add Atlas custom properties theme ([868a5cf](https://github.com/liferay/liferay-portal/commit/868a5cf310d35d0ea06ade8e0882057710af3028))
- LPD-81854 @clayui/css atlas variable import order should match base variable import order ([1c52c57](https://github.com/liferay/liferay-portal/commit/1c52c57b0f9427edea79b6116ec9dc2d0a2a87df))
- LPD-86312 @clayui/css remove $enable-atlas-custom-properties, atlas-custom-properties should be manually imported ([b62b47f](https://github.com/liferay/liferay-portal/commit/b62b47fd3f7ab9dc1081c961890c06cc29b4e161))
- LPD-66974 Add focus ring animation ([1e1cfc1](https://github.com/liferay/liferay-portal/commit/1e1cfc13bb7f2e92b5a6582c443a1418337cf1a0))
- LPD-66974 Add focus ring animation for atlas-custom-properties ([b65ded6](https://github.com/liferay/liferay-portal/commit/b65ded6af7754d950e3f65c89e68716d2b39bab2))
- LPD-66973 Retrigger focus ring animation on browser tab return ([886d229](https://github.com/liferay/liferay-portal/commit/886d2292438211b68c328c729542e23d1319bb67))
- LPD-66973 Avoid animation conflict and improve tab returning effect ([9f24b66](https://github.com/liferay/liferay-portal/commit/9f24b66a91eef63489c5ab44fd762ab3735f8bd1))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-86387 Update Clay versions across modules ([8cd5677](https://github.com/liferay/liferay-portal/commit/8cd5677d5089f81244b4e390b4cd300cbf8aedf9))
- LPD-78369 Scale label font-size and line-height inside form-group-sm ([cdf2ffa](https://github.com/liferay/liferay-portal/commit/cdf2ffae847ba432c9f5651e13ab769c9db05178))
- LPD-78369 Add atlas-custom-properties variables and refactor test assertions ([fbaa442](https://github.com/liferay/liferay-portal/commit/fbaa442762bcfebbc2293b3225dcd697b676176e))
- LPD-73275 Use map-deep-merge for breadcrumbs ([a27b889](https://github.com/liferay/liferay-portal/commit/a27b889928aefed5e145d39096190a5d6b8239d8))
- LPD-81847 Show cadmin focus shadow on DualListBox selects ([75f0156](https://github.com/liferay/liferay-portal/commit/75f0156f47b3306e86e54ff0fbd1a01a39e2a1e5))
- LPD-81847 Show atlas focus shadow on DualListBox selects ([79428fb](https://github.com/liferay/liferay-portal/commit/79428fb3543810b1805c0beed451192b6715a7f7))
- LPD-81847 Show atlas custom properties focus shadow on DualListBox selects ([316be96](https://github.com/liferay/liferay-portal/commit/316be9666fa58c4893054510a20b12e3ebfbd1f4))
- LPD-81847 SF ([29b8d6f](https://github.com/liferay/liferay-portal/commit/29b8d6fba6010e2ad04e173275ea5fe368f9536d))
- LPD-77874 Anchor breadcrumb collapse toggle to first row of items ([94bf1bf](https://github.com/liferay/liferay-portal/commit/94bf1bf19bd38e8592963482b7f3853bd318327b))
- LPD-77874 Fix breadcrumb toggle click area and icon alignment ([57a90c0](https://github.com/liferay/liferay-portal/commit/57a90c0f2944c95067a5dadc61342a4fa7ba2c47))
- LPD-77874 SF ([e88e459](https://github.com/liferay/liferay-portal/commit/e88e4598f58142cc9f7037106c5aa65c91ecedfa))
- LPD-44626 Reset descendant letter-spacing on menubar-transparent links ([f30c8cf](https://github.com/liferay/liferay-portal/commit/f30c8cf592329110ac6af1eb5894abbb4ac4f171))
- LPD-88394 Prevent focus ring from displacing toggle-switch ([d108807](https://github.com/liferay/liferay-portal/commit/d108807a4277c4765d541657f731b07009b647f2))
- LPD-88394 Fix focus ring animation ([a1469eb](https://github.com/liferay/liferay-portal/commit/a1469eb0909977c97fef5c6349d71185823acdf9))
- LPD-40038 @clayui/css add arrow-key-{up,down,left,right}.svg ([be98ebd](https://github.com/liferay/liferay-portal/commit/be98ebdc4af4ec80bc65dfe22d794f03422a55a3))
- LPD-40038 @clayui/core add KeyboardArrowsIndicator ([79010f2](https://github.com/liferay/liferay-portal/commit/79010f2ec8d9e13f092e088cd161b37c32892a06))
- LPD-88290 New styles for the resize handle ([c66e7cb](https://github.com/liferay/liferay-portal/commit/c66e7cbfd58c4a5b0b911d8b8fdfdaf84e579861))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/data-provider

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-82604 Fix clay-data-provider test with CacheFirst, it was expecting the cache to be already populated ([38fb06d](https://github.com/liferay/liferay-portal/commit/38fb06d2913f0b985ae53010ff60de76601ad0c8))
- LPD-82604 Fix DataClient, which didn't provide a method to clear completed promises, breaking the CacheAndNetwork fetch policy ([b99f481](https://github.com/liferay/liferay-portal/commit/b99f481a35f2da518491229e6a78607bd6920213))
- LPD-82604 Fix clay-data-provider test with CacheAndNetwork, it was expecting the cache to be already populated ([337d076](https://github.com/liferay/liferay-portal/commit/337d07695378423d84cd4aace237df41cbf49cca))
- LPD-82604 Fix clay-data-provider test using Node.js-specific unref method, adapting it to reflect the browser environment ([a540d95](https://github.com/liferay/liferay-portal/commit/a540d95ebaffac1d8b31b5d53741ab2e88a8add4))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/date-picker

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/drop-down

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-82700 Conditionally add aria-controls attribute to ClayDropDown trigger, so it won't reference a element that isn't present in the DOM ([0b476ba](https://github.com/liferay/liferay-portal/commit/0b476bac00fa0482cc266f2f7ff4628213333350))
- LPD-82700 Remove aria-labelledby for DropDown groups without header ([dd8f061](https://github.com/liferay/liferay-portal/commit/dd8f061b49d3a731d14a2e101e6abed533ff135b))
- LPD-83370 Enable DropDownWithDrilldown skipped tests ([aefacb2](https://github.com/liferay/liferay-portal/commit/aefacb276312ec759f3554e41b37f04932b1f067))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/empty-state

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/form

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-80237 Allow passing refs to the Clay Select ([55e2649](https://github.com/liferay/liferay-portal/commit/55e2649bdc0aef25991539f1d4fa9c4e4a212645))
- LPD-83371 Unskip test. Use the callback function to test the multiple options ([c13571d](https://github.com/liferay/liferay-portal/commit/c13571d350579998edfa8aa6228f778f8d81e74a))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/icon

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-78750 @clayui.com add rocket.svg to docs ([e74d242](https://github.com/liferay/liferay-portal/commit/e74d2429a8f8c1b6fc1e328bd68ff82322f8fc22))
- LPD-80374 clayui.com add aliases for calculator, price-list, product-configuration and products icons ([c47704a](https://github.com/liferay/liferay-portal/commit/c47704a0a6032017996175da941ab6062fed9ff9))
- LPD-80439 clayui.com document layout-new-window.svg ([7422fbf](https://github.com/liferay/liferay-portal/commit/7422fbfd99740a28d6c088cddec5dcaf3fa77652))
- LPD-83068 clayui.com add books-brush.svg and aliases ([5e950e1](https://github.com/liferay/liferay-portal/commit/5e950e19fe9fa530ee36a2813a6d4ce8f06b31ef))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-40038 clayui.com add arrow-key-* and aliases ([9d2c4fd](https://github.com/liferay/liferay-portal/commit/9d2c4fd657bc19a56cdc288c5e71887757f4be94))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/label

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/layout

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/link

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/list

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/loading-indicator

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/localized-input

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/management-toolbar

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/modal

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-79663 Only render items if elements are provided ([20f3b19](https://github.com/liferay/liferay-portal/commit/20f3b193ef3a1cf1d5ee43a8a7bcaaee4bcd2dd2))
- LPD-79663 Add test ([d5d832d](https://github.com/liferay/liferay-portal/commit/d5d832d6c11e94cacbb78973c93443fe8e10906d))
- LPD-79663 Update snapshots ([b4906d5](https://github.com/liferay/liferay-portal/commit/b4906d5be205e68db99cb415e6da2ff850ea59d8))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/multi-select

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-83372 Enable skipped clay-multi-select tests ([3764509](https://github.com/liferay/liferay-portal/commit/3764509a95729468bc1054782d6a9a406b25b665))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-87534 Update test snapshots ([63e0b4a](https://github.com/liferay/liferay-portal/commit/63e0b4a9515f297790345f61717ce2841a79bf75))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/multi-step-nav

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-79381 Add as prop to Indicator to support custom tags ([626fe1a](https://github.com/liferay/liferay-portal/commit/626fe1acdc2106be3690f87c63820036b2a30511))
- LPD-79381 Add buttonProps and disabled support to MultiStepNav Indicator ([be6b4d5](https://github.com/liferay/liferay-portal/commit/be6b4d576a807c53d0dd85714b0ddbe4ce92cb86))
- LPD-79381 Add disabled props to the step Item ([cc280ca](https://github.com/liferay/liferay-portal/commit/cc280ca56bd641c07359db4be0e904cd4eff9e0b))
- LPD-79381 Add Disabled and NonInteractive stories to MultiStepNav ([d5db4bc](https://github.com/liferay/liferay-portal/commit/d5db4bc68041990beea9d0eac0a6633af135bb72))
- LPD-79381 Remove deprecated complete prop from MultiStepNav stories ([cbfed5f](https://github.com/liferay/liferay-portal/commit/cbfed5fd56bd3c52a688fd66fd4cf09c502ea1a7))
- LPD-79381 Respect 'as' prop and replace buttonProps with elementProps ([ffe7a0a](https://github.com/liferay/liferay-portal/commit/ffe7a0afabedbab5b236483bab2c30a0d5b2c58e))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/nav

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-78996 @clayui/nav VerticalNav should allow stacked navs with no indent ([d824c08](https://github.com/liferay/liferay-portal/commit/d824c08a994b3babfd22902dcb739a9f0ed11e26))
- LPD-82605 Remove skip from tests, they pass ([228e368](https://github.com/liferay/liferay-portal/commit/228e3681671e3f3907f714b2f96b96dc15b9c0fd))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/navigation-bar

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/pagination

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-83387 Add global library ([b9fd33b](https://github.com/liferay/liferay-portal/commit/b9fd33b2f37a3f52c130ece8e208d1f7e0941b56))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/pagination-bar

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/panel

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/popover

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-85584 Fix focus not returning to trigger after popover blur | When navigating via keyboard, focus moves to the popover when it opens. If a blur event occurs, focus is lost instead of returning to the previous element. This fix ensures that focus returns to the trigger element. ([29d35b0](https://github.com/liferay/liferay-portal/commit/29d35b01cf9bd77033f454e45d041493b1202b47))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/progress-bar

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-79357 Add classname to progress bar ([cab0a14](https://github.com/liferay/liferay-portal/commit/cab0a14942e3ec5c95ece4f8671e51839297f65e))
- LPD-79357 @clayui/progress-bar adds fillBarClassName example in Storybook ([5130f70](https://github.com/liferay/liferay-portal/commit/5130f706f8c16440be3edece982c6f4f53e10455))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/provider

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-82604 Fix DataClient, which didn't provide a method to clear completed promises, breaking the CacheAndNetwork fetch policy ([b99f481](https://github.com/liferay/liferay-portal/commit/b99f481a35f2da518491229e6a78607bd6920213))
- LPD-66973 Retrigger focus ring animation on browser tab return ([886d229](https://github.com/liferay/liferay-portal/commit/886d2292438211b68c328c729542e23d1319bb67))
- LPD-66973 Avoid animation conflict and improve tab returning effect ([9f24b66](https://github.com/liferay/liferay-portal/commit/9f24b66a91eef63489c5ab44fd762ab3735f8bd1))
- LPD-66973 Use constants to avoid CSS class repetition ([565d0a2](https://github.com/liferay/liferay-portal/commit/565d0a26c78727edc74d538262fc66f8b07ebbc6))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/shared

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-82606 Use fireEvent. userEvent was triggering keyups between ArrowRight events so the last part about acceleration was never working ([0c313b9](https://github.com/liferay/liferay-portal/commit/0c313b9533b46e30ab078777b03b5e45dc9b4b6b))
- LPD-84613 Move the usePanelWidthMax hook to clay-shared and rename it to useObservedMaxWidth ([6f6c2a7](https://github.com/liferay/liferay-portal/commit/6f6c2a771008635ed912dda53edf72240fdae6aa))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-69776 Rename useOverlayPositon to useOverlayPosition ([7152aae](https://github.com/liferay/liferay-portal/commit/7152aae19e8be4380d785f0c2bd7610923e3306c))
- LPD-69776 Reset sourceElement positioning ([435b602](https://github.com/liferay/liferay-portal/commit/435b602a0f282ec1b77267edd4eff65827d48214))
- LPD-69776 Add test coverage to doAlign positioning reset ([b137c44](https://github.com/liferay/liferay-portal/commit/b137c44f0976324e171159adcbad5aa7abe08851))
- LPD-88290 Move PanelResizer to clay-core ([ddde603](https://github.com/liferay/liferay-portal/commit/ddde603ae4e42d51ceb23cbbd8a117f42e0662cc))
- LPD-88290 Rename PanelResizer by ResizeHandle ([d2453ea](https://github.com/liferay/liferay-portal/commit/d2453ea7bf7321cdcf04b9681cca1ca2447074d0))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/slider

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/sticker

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-78752 Add product icon ([5f5fb3f](https://github.com/liferay/liferay-portal/commit/5f5fb3fc32d41276098bfd249fd0080f46ed062c))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/table

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/tabs

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-83389 Remove tests related to modern and displayType props for ClayTabs since these attributes are not supported anymore and not used by the List component since Apr 10 2023 ([0cfc6a7](https://github.com/liferay/liferay-portal/commit/0cfc6a77f01c76e7e72fd42cc132bb23ea0c5c55))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/time-picker

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/toolbar

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/tooltip

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

## @clayui/upper-toolbar

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-82700 Conditionally add aria-controls attribute to ClayDropDown trigger, so it won't reference a element that isn't present in the DOM ([0b476ba](https://github.com/liferay/liferay-portal/commit/0b476bac00fa0482cc266f2f7ff4628213333350))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-89587 Bump Clay versions to 3.162.0 ([3eaf9a8](https://github.com/liferay/liferay-portal/commit/3eaf9a8a13c95f63fffd9b38996001879beefb13))

# [3.162.0] (2026-05-11)

## @clayui/alert

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-81627 Allow passing classNames to the container ([1f9f4da](https://github.com/liferay/liferay-portal/commit/1f9f4da502edcfdd557a19645e383624cf9bb1fc))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))

## @clayui/autocomplete

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-82600 Fix Escape key input ([e02bcac](https://github.com/liferay/liferay-portal/commit/e02bcac12cbca7dd8d379f2b6a1ab81928aab56b))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))

## @clayui/badge

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-83029 Allow passing icon to badge ([26ceeb3](https://github.com/liferay/liferay-portal/commit/26ceeb3731afc917d0621aea45d967f16d2df0e5))
- LPD-83029 Add test ([ffa79b5](https://github.com/liferay/liferay-portal/commit/ffa79b5ed6336425bf6a5e9e06574a78ece42b58))
- LPD-83029 Implement ItemAfter, ItemBefore and ItemExpand logic ([19fcf1f](https://github.com/liferay/liferay-portal/commit/19fcf1f41c0cbb34cefb00bcd51f9cfcce7722b1))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))

## @clayui/breadcrumb

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))

## @clayui/button

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))

## @clayui/card

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-82700 Conditionally add aria-controls attribute to ClayDropDown trigger, so it won't reference a element that isn't present in the DOM ([0b476ba](https://github.com/liferay/liferay-portal/commit/0b476bac00fa0482cc266f2f7ff4628213333350))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-83803 Make ClayCardWithInfo img not draggable ([e6b9674](https://github.com/liferay/liferay-portal/commit/e6b9674a2d9f3ed65f2173fae2c7b0f00ed27940))
- LPD-83803 Update test snapshots ([208ed9b](https://github.com/liferay/liferay-portal/commit/208ed9bde0b51d3e2f1e0b84331394944c607130))

## @clayui/color-picker

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-82601 Remove skip, test is working fine ([e0ad6fe](https://github.com/liferay/liferay-portal/commit/e0ad6fee201ce87c88db7b00453a265ee9c35785))
- LPD-80236 Convert index.ts into barrel file for exports ([ca8603a](https://github.com/liferay/liferay-portal/commit/ca8603a4049edfe93d16ad4b4dfa618362e35030))
- LPD-80236 Add useColorPicker hook ([82a441e](https://github.com/liferay/liferay-portal/commit/82a441e1e9a3969ae9e09d19e46607e304707916))
- LPD-80236 Add Field component to isolate the color input, splotch and dropdown ([74de4f2](https://github.com/liferay/liferay-portal/commit/74de4f237c92208742c97a8d25aae7157cb40375))
- LPD-80236 Export useColorPicker hook, Field and Editor components to reuse them ([2b55a42](https://github.com/liferay/liferay-portal/commit/2b55a42394e109edf4e454a4ea8e1cffd01cbe39))
- LPD-80236 Add the ability to add an external trigger reference ([13892d2](https://github.com/liferay/liferay-portal/commit/13892d25edd143ec7204c5c96b6870058ea9993a))
- LPD-80236 Adapt the parseColorValue function to the ClayColorPicker, taking into account the hex6 and hex8 ([c9d85e9](https://github.com/liferay/liferay-portal/commit/c9d85e96be51906c6c8699d95936255486f17418))
- LPD-80236 Save the value when onBlur is done in the Editor hex input ([ab85832](https://github.com/liferay/liferay-portal/commit/ab85832fee860e17e1a6df45b397987055a23059))
- LPD-80236 Improve props and descriptions ([6da7abb](https://github.com/liferay/liferay-portal/commit/6da7abb3a3dc9f6afcbf4eb24febbe592d4af6d9))
- LPD-80236 Rename onClickSplotch to onSplotchClick ([bec76ac](https://github.com/liferay/liferay-portal/commit/bec76acc04c3bbd9ed357adf790094fde7f09e29))
- LPD-80236 Create new function to format the hex colors ([bed726a](https://github.com/liferay/liferay-portal/commit/bed726a2fb3ce44c76a4157ac4adb2c93a77de40))
- LPD-83988 Export all the necessary functions from util.ts ([fdd7b2c](https://github.com/liferay/liferay-portal/commit/fdd7b2c82fcd839f34d5f5f7f29bd27b827c74c4))
- LPD-83988 Update the package types in the color-picker docs ([d2b0084](https://github.com/liferay/liferay-portal/commit/d2b00844c744b7175051336914471d4dd3d19957))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))

## @clayui/core

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-78752 Allow configuring custom classes in Clay SidePanel.Title ([5626239](https://github.com/liferay/liferay-portal/commit/56262393602378d933b9d7962fc609700b4e8407))
- LPD-79027 Use existing onEnd callback ([d6d039d](https://github.com/liferay/liferay-portal/commit/d6d039d7073cbc6558ca4ab14bc08eb372c035bb))
- LPD-79027 Reset state also after drop with mouse ([03de461](https://github.com/liferay/liferay-portal/commit/03de46132d6ac765df42adc6452da3ab3280a493))
- LPD-78996 @clayui/nav VerticalNav should allow stacked navs with no indent ([d824c08](https://github.com/liferay/liferay-portal/commit/d824c08a994b3babfd22902dcb739a9f0ed11e26))
- LPD-78996 @clayui/core VerticalNav add example without indent ([60adeec](https://github.com/liferay/liferay-portal/commit/60adeec5ac228a6ae1e7a39042c3770a6c0c9a9d))
- LPD-79543 Allow side panel to disable the escape key behavior ([1aa3734](https://github.com/liferay/liferay-portal/commit/1aa3734ee319c84899b114eef93587bdf0fd9658))
- LPD-66630 Update size to small ([096b025](https://github.com/liferay/liferay-portal/commit/096b02530c74447b42f14aa7383aeef733559b6f))
- LPD-66630 Update snapshots ([4f5e77b](https://github.com/liferay/liferay-portal/commit/4f5e77b42757bc9a0d4df11a6a6826156bfaa976))
- LPD-79219 @clayui/core Treeview drag handler should be 24px x 24px when focused ([2cc084a](https://github.com/liferay/liferay-portal/commit/2cc084ae2a760b05df962eff3aff1e9b920383ba))
- LPD-75776 Fix typo ([9d38ea9](https://github.com/liferay/liferay-portal/commit/9d38ea9f9a3745e95d519a66ece726ad13fcf40d))
- LPD-81627 Make symbol optional ([2343e95](https://github.com/liferay/liferay-portal/commit/2343e95f6dc7d6d4f0d3fd6b2d4b1220b2002291))
- LPD-82602 Fix keyboard events ([cde7716](https://github.com/liferay/liferay-portal/commit/cde7716b174daa483aa7724e28846ccc94eb246c))
- LPD-82700 Conditionally add aria-controls attribute to ClayDropDown trigger, so it won't reference a element that isn't present in the DOM ([0b476ba](https://github.com/liferay/liferay-portal/commit/0b476bac00fa0482cc266f2f7ff4628213333350))
- LPD-82603 Fix escape key input in skipped side panel test ([e0029ee](https://github.com/liferay/liferay-portal/commit/e0029eec3a6084e0a952a4f4e5f7c1fb66e59e21))
- LPD-80239 Add FocusTrap to the dropdown menu ([0a906b5](https://github.com/liferay/liferay-portal/commit/0a906b542b5475edf2e05f4ed04eaf61dd23967b))
- LPD-85711 Add opt-in search capability to the Picker component ([f14c1f9](https://github.com/liferay/liferay-portal/commit/f14c1f94459d7a0876c7a5dfa51bf8df6b328daf))
- LPD-85711 Add test coverage to Picker search capability ([8201b1e](https://github.com/liferay/liferay-portal/commit/8201b1e67ea5acfd5ce9d292a022ec518b041d79))
- LPD-85711 Update Picker documentation to include the new search capability ([d465000](https://github.com/liferay/liferay-portal/commit/d4650005e444fb892fd2af4dfa1bab4ae9634e73))
- LPD-85711 Refactor Picker component to modularize search logic ([4c7178e](https://github.com/liferay/liferay-portal/commit/4c7178e991a4abe6dd5b843f3f80f23bf6ec8ef3))
- LPD-85711 Improve search accessibility ([a8a06e0](https://github.com/liferay/liferay-portal/commit/a8a06e0f6a5a61196a6dec4ead16716f5d423720))
- LPD-85711 Reorganize props ([1fca9cd](https://github.com/liferay/liferay-portal/commit/1fca9cd03e891910f79f92a26491d3f2ce42c242))
- LPD-84613 Move the usePanelWidthMax hook to clay-shared and rename it to useObservedMaxWidth ([6f6c2a7](https://github.com/liferay/liferay-portal/commit/6f6c2a771008635ed912dda53edf72240fdae6aa))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-85564 Add onItemActivate prop and default Enter behavior ([103eb11](https://github.com/liferay/liferay-portal/commit/103eb11ddbad83d7d93cb17cb72eb282ac0bfe2c))
- LPD-85564 Add tests ([c7be8b1](https://github.com/liferay/liferay-portal/commit/c7be8b1e18a5dfca19d8a8969af2a9aaf4b24242))
- LPD-38804 Add type=button to ClayPicker options ([cb6c140](https://github.com/liferay/liferay-portal/commit/cb6c14083e24ed07eb3170a85a278175959cd2cf))
- LPD-38804 Keep selected item label during search ([25a0b52](https://github.com/liferay/liferay-portal/commit/25a0b52c4ca1685de603750adb462eb09d4bc3cc))
- LPD-74518 Scroll the selected option into view when opening Picker ([de93377](https://github.com/liferay/liferay-portal/commit/de93377f714692bffb044553db021496d8e07901))
- LPD-74518 Compute scroll offset via getBoundingClientRect ([380ba94](https://github.com/liferay/liferay-portal/commit/380ba94749e53ce55c082ce1caea03820713c3e4))
- LPD-74518 Scope item lookup to listRef and simplify key fallback ([be03e9f](https://github.com/liferay/liferay-portal/commit/be03e9fe3a4ced90c458b3c741ec8d3f0c80690b))
- LPD-40038 @clayui/core add KeyboardArrowsIndicator ([79010f2](https://github.com/liferay/liferay-portal/commit/79010f2ec8d9e13f092e088cd161b37c32892a06))
- LPD-88290 Move PanelResizer to clay-core ([ddde603](https://github.com/liferay/liferay-portal/commit/ddde603ae4e42d51ceb23cbbd8a117f42e0662cc))
- LPD-88290 Rename PanelResizer by ResizeHandle ([d2453ea](https://github.com/liferay/liferay-portal/commit/d2453ea7bf7321cdcf04b9681cca1ca2447074d0))
- LPD-88290 Adapt imports ([04a0ea2](https://github.com/liferay/liferay-portal/commit/04a0ea2f7b0fa654e3ffe6cdc1c602dac1ab897b))
- LPD-88290 Rename component props and decouple from dependent component ([6636802](https://github.com/liferay/liferay-portal/commit/66368028e373ba1ace49ec8bc036a0856bc46f7d))
- LPD-88290 New styles for the resize handle ([c66e7cb](https://github.com/liferay/liferay-portal/commit/c66e7cbfd58c4a5b0b911d8b8fdfdaf84e579861))
- LPD-88747 Add storybook ([c34b520](https://github.com/liferay/liferay-portal/commit/c34b52058d1d38b409d2ca1c37d724412dd231ca))
- LPD-88746 Add documentation ([c1d7c3e](https://github.com/liferay/liferay-portal/commit/c1d7c3ea984d714823126abe9a119f1847e24112))
- LPD-88290 Add RTL compatibility ([af2828c](https://github.com/liferay/liferay-portal/commit/af2828cb9f4f6b354a34110e1664623f5e3bf1cf))

## @clayui/css

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-75997 @clayui/css Update product-menu-open.svg ([1db5459](https://github.com/liferay/liferay-portal/commit/1db545935c65cc6ca37ae37b57f27951d42405f6))
- LPD-79073 Control Menu button icons should be 16px ([36a0f8a](https://github.com/liferay/liferay-portal/commit/36a0f8adaba142c08d1ac8c460ed919ee81ff9c9))
- LPD-77928 @clayui/css SF variable files ([1fe4e68](https://github.com/liferay/liferay-portal/commit/1fe4e6817515190e3f41d435bc62f3436ba59c0d))
- LPD-79018 @clayui/css sticker-xxl icon should be 40px ([2fd0409](https://github.com/liferay/liferay-portal/commit/2fd0409656bdda0f9e2f765891de2a45660e4237))
- LPD-80362 @clayui/core Treeview icon angle-down should display larger ([22eb0b3](https://github.com/liferay/liferay-portal/commit/22eb0b3c7b87daa3ee9d61538dad07303d844537))
- LPD-78750 @clayui/css adds rocket.svg ([340f130](https://github.com/liferay/liferay-portal/commit/340f13097d6f9f3884532d483edaf83b5c6dd3f3))
- LPD-79219 @clayui/core Treeview drag handler should be 24px x 24px when focused ([2cc084a](https://github.com/liferay/liferay-portal/commit/2cc084ae2a760b05df962eff3aff1e9b920383ba))
- LPD-80374 @clayui/css add icons calculator, price-list, product-configuration, and products ([cffbeff](https://github.com/liferay/liferay-portal/commit/cffbeff59e3509c2b132969f49d4d5ce1898c232))
- LPD-80374 SF icons.svg ([c27b09c](https://github.com/liferay/liferay-portal/commit/c27b09c03777ee017fe66d7ebd5a93e66ff4e3fc))
- LPD-77810 @clayui/css Replace Sass math with CSS calc ([75730ce](https://github.com/liferay/liferay-portal/commit/75730ce27d3621b0c73c5256be4568891d44eec8))
- LPD-77810 @clayui/css Replace math-sign with CSS calc ([42a8656](https://github.com/liferay/liferay-portal/commit/42a865608c05a36c2d2fd5b313b733a8d0709726))
- LPD-77810 @clayui/css SF ([4eb1545](https://github.com/liferay/liferay-portal/commit/4eb154584aeb8bdbd82038223b5a719b81769282))
- LPD-80439 @clayui/css add layout-new-window.svg ([2310a7f](https://github.com/liferay/liferay-portal/commit/2310a7f5800515f39300f77362ccf8415d202c0f))
- LPD-79381 Scope cursor and hover effects to interactive elements ([8ae76f2](https://github.com/liferay/liferay-portal/commit/8ae76f2912116397bdad8bade5e7c8e6faa7c26c))
- LPD-81799 - Regen ([e577af8](https://github.com/liferay/liferay-portal/commit/e577af8942abc4abfc9811ba91781239cf08ef6d))
- LPD-81799 - SF ([3a6f4e5](https://github.com/liferay/liferay-portal/commit/3a6f4e5c95e8e68de19e2902eb8b673311a241c2))
- LPD-80549 @clayui/css clay-css mixin should support any CSS property ([49fc8b8](https://github.com/liferay/liferay-portal/commit/49fc8b8ad1a059fad8facc1f5e164c1373466270))
- LPD-81799 clayui.com should use local version of icons.svg in dev mode ([d57b27c](https://github.com/liferay/liferay-portal/commit/d57b27cec0cf0098114a233eced849776d0d9cd5))
- LPD-81820 @clayui/css add unit to 0 values to make it work with calc() ([9a5ffbf](https://github.com/liferay/liferay-portal/commit/9a5ffbffbb75908bc23dae70b177bcf434507fab))
- LPD-82273 @clayui/css remove use of Sass nth function with variables that could contain a CSS variable ([503c60e](https://github.com/liferay/liferay-portal/commit/503c60e225c5a2b223c7476fc007325d86172433))
- LPD-77641 @clayui/css $custom-control-indicator-checked-color should propagate to checkbox and radio ([02fe938](https://github.com/liferay/liferay-portal/commit/02fe938e328af2c1a472d06ae8ab20d55d018242))
- LPD-83068 @clayui/css add books-brush.svg ([3d3f1b2](https://github.com/liferay/liferay-portal/commit/3d3f1b221cf72464d7f28ec9ea0c907c8775cf31))
- LPD-71471 @clayui/css clay-data-label-text-position function shouldn't error with CSS variables ([a88e120](https://github.com/liferay/liferay-portal/commit/a88e1203878e72258f746b31633cfd03b3666090))
- LPD-71471 @clayui/css Use calc for $spacers ([3a8d947](https://github.com/liferay/liferay-portal/commit/3a8d9476d2c67482bc535ebf16aad92fa46f9f7a))
- LPD-71471 @clayui/css Add Atlas custom properties theme ([868a5cf](https://github.com/liferay/liferay-portal/commit/868a5cf310d35d0ea06ade8e0882057710af3028))
- LPD-81854 @clayui/css atlas variable import order should match base variable import order ([1c52c57](https://github.com/liferay/liferay-portal/commit/1c52c57b0f9427edea79b6116ec9dc2d0a2a87df))
- LPD-86312 @clayui/css remove $enable-atlas-custom-properties, atlas-custom-properties should be manually imported ([b62b47f](https://github.com/liferay/liferay-portal/commit/b62b47fd3f7ab9dc1081c961890c06cc29b4e161))
- LPD-66974 Add focus ring animation ([1e1cfc1](https://github.com/liferay/liferay-portal/commit/1e1cfc13bb7f2e92b5a6582c443a1418337cf1a0))
- LPD-66974 Add focus ring animation for atlas-custom-properties ([b65ded6](https://github.com/liferay/liferay-portal/commit/b65ded6af7754d950e3f65c89e68716d2b39bab2))
- LPD-66973 Retrigger focus ring animation on browser tab return ([886d229](https://github.com/liferay/liferay-portal/commit/886d2292438211b68c328c729542e23d1319bb67))
- LPD-66973 Avoid animation conflict and improve tab returning effect ([9f24b66](https://github.com/liferay/liferay-portal/commit/9f24b66a91eef63489c5ab44fd762ab3735f8bd1))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-86387 Update Clay versions across modules ([8cd5677](https://github.com/liferay/liferay-portal/commit/8cd5677d5089f81244b4e390b4cd300cbf8aedf9))
- LPD-78369 Scale label font-size and line-height inside form-group-sm ([cdf2ffa](https://github.com/liferay/liferay-portal/commit/cdf2ffae847ba432c9f5651e13ab769c9db05178))
- LPD-78369 Add atlas-custom-properties variables and refactor test assertions ([fbaa442](https://github.com/liferay/liferay-portal/commit/fbaa442762bcfebbc2293b3225dcd697b676176e))
- LPD-73275 Use map-deep-merge for breadcrumbs ([a27b889](https://github.com/liferay/liferay-portal/commit/a27b889928aefed5e145d39096190a5d6b8239d8))
- LPD-81847 Show cadmin focus shadow on DualListBox selects ([75f0156](https://github.com/liferay/liferay-portal/commit/75f0156f47b3306e86e54ff0fbd1a01a39e2a1e5))
- LPD-81847 Show atlas focus shadow on DualListBox selects ([79428fb](https://github.com/liferay/liferay-portal/commit/79428fb3543810b1805c0beed451192b6715a7f7))
- LPD-81847 Show atlas custom properties focus shadow on DualListBox selects ([316be96](https://github.com/liferay/liferay-portal/commit/316be9666fa58c4893054510a20b12e3ebfbd1f4))
- LPD-81847 SF ([29b8d6f](https://github.com/liferay/liferay-portal/commit/29b8d6fba6010e2ad04e173275ea5fe368f9536d))
- LPD-77874 Anchor breadcrumb collapse toggle to first row of items ([94bf1bf](https://github.com/liferay/liferay-portal/commit/94bf1bf19bd38e8592963482b7f3853bd318327b))
- LPD-77874 Fix breadcrumb toggle click area and icon alignment ([57a90c0](https://github.com/liferay/liferay-portal/commit/57a90c0f2944c95067a5dadc61342a4fa7ba2c47))
- LPD-77874 SF ([e88e459](https://github.com/liferay/liferay-portal/commit/e88e4598f58142cc9f7037106c5aa65c91ecedfa))
- LPD-44626 Reset descendant letter-spacing on menubar-transparent links ([f30c8cf](https://github.com/liferay/liferay-portal/commit/f30c8cf592329110ac6af1eb5894abbb4ac4f171))
- LPD-88394 Prevent focus ring from displacing toggle-switch ([d108807](https://github.com/liferay/liferay-portal/commit/d108807a4277c4765d541657f731b07009b647f2))
- LPD-88394 Fix focus ring animation ([a1469eb](https://github.com/liferay/liferay-portal/commit/a1469eb0909977c97fef5c6349d71185823acdf9))
- LPD-40038 @clayui/css add arrow-key-{up,down,left,right}.svg ([be98ebd](https://github.com/liferay/liferay-portal/commit/be98ebdc4af4ec80bc65dfe22d794f03422a55a3))
- LPD-40038 @clayui/core add KeyboardArrowsIndicator ([79010f2](https://github.com/liferay/liferay-portal/commit/79010f2ec8d9e13f092e088cd161b37c32892a06))
- LPD-88290 New styles for the resize handle ([c66e7cb](https://github.com/liferay/liferay-portal/commit/c66e7cbfd58c4a5b0b911d8b8fdfdaf84e579861))

## @clayui/data-provider

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-82604 Fix clay-data-provider test with CacheFirst, it was expecting the cache to be already populated ([38fb06d](https://github.com/liferay/liferay-portal/commit/38fb06d2913f0b985ae53010ff60de76601ad0c8))
- LPD-82604 Fix DataClient, which didn't provide a method to clear completed promises, breaking the CacheAndNetwork fetch policy ([b99f481](https://github.com/liferay/liferay-portal/commit/b99f481a35f2da518491229e6a78607bd6920213))
- LPD-82604 Fix clay-data-provider test with CacheAndNetwork, it was expecting the cache to be already populated ([337d076](https://github.com/liferay/liferay-portal/commit/337d07695378423d84cd4aace237df41cbf49cca))
- LPD-82604 Fix clay-data-provider test using Node.js-specific unref method, adapting it to reflect the browser environment ([a540d95](https://github.com/liferay/liferay-portal/commit/a540d95ebaffac1d8b31b5d53741ab2e88a8add4))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))

## @clayui/date-picker

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))

## @clayui/drop-down

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-82700 Conditionally add aria-controls attribute to ClayDropDown trigger, so it won't reference a element that isn't present in the DOM ([0b476ba](https://github.com/liferay/liferay-portal/commit/0b476bac00fa0482cc266f2f7ff4628213333350))
- LPD-82700 Remove aria-labelledby for DropDown groups without header ([dd8f061](https://github.com/liferay/liferay-portal/commit/dd8f061b49d3a731d14a2e101e6abed533ff135b))
- LPD-83370 Enable DropDownWithDrilldown skipped tests ([aefacb2](https://github.com/liferay/liferay-portal/commit/aefacb276312ec759f3554e41b37f04932b1f067))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))

## @clayui/empty-state

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))

## @clayui/form

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-80237 Allow passing refs to the Clay Select ([55e2649](https://github.com/liferay/liferay-portal/commit/55e2649bdc0aef25991539f1d4fa9c4e4a212645))
- LPD-83371 Unskip test. Use the callback function to test the multiple options ([c13571d](https://github.com/liferay/liferay-portal/commit/c13571d350579998edfa8aa6228f778f8d81e74a))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))

## @clayui/icon

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-78750 @clayui.com add rocket.svg to docs ([e74d242](https://github.com/liferay/liferay-portal/commit/e74d2429a8f8c1b6fc1e328bd68ff82322f8fc22))
- LPD-80374 clayui.com add aliases for calculator, price-list, product-configuration and products icons ([c47704a](https://github.com/liferay/liferay-portal/commit/c47704a0a6032017996175da941ab6062fed9ff9))
- LPD-80439 clayui.com document layout-new-window.svg ([7422fbf](https://github.com/liferay/liferay-portal/commit/7422fbfd99740a28d6c088cddec5dcaf3fa77652))
- LPD-83068 clayui.com add books-brush.svg and aliases ([5e950e1](https://github.com/liferay/liferay-portal/commit/5e950e19fe9fa530ee36a2813a6d4ce8f06b31ef))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-40038 clayui.com add arrow-key-* and aliases ([9d2c4fd](https://github.com/liferay/liferay-portal/commit/9d2c4fd657bc19a56cdc288c5e71887757f4be94))

## @clayui/label

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))

## @clayui/layout

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))

## @clayui/link

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))

## @clayui/list

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))

## @clayui/loading-indicator

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))

## @clayui/localized-input

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))

## @clayui/management-toolbar

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))

## @clayui/modal

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-79663 Only render items if elements are provided ([20f3b19](https://github.com/liferay/liferay-portal/commit/20f3b193ef3a1cf1d5ee43a8a7bcaaee4bcd2dd2))
- LPD-79663 Add test ([d5d832d](https://github.com/liferay/liferay-portal/commit/d5d832d6c11e94cacbb78973c93443fe8e10906d))
- LPD-79663 Update snapshots ([b4906d5](https://github.com/liferay/liferay-portal/commit/b4906d5be205e68db99cb415e6da2ff850ea59d8))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))

## @clayui/multi-select

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-83372 Enable skipped clay-multi-select tests ([3764509](https://github.com/liferay/liferay-portal/commit/3764509a95729468bc1054782d6a9a406b25b665))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))

## @clayui/multi-step-nav

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-79381 Add as prop to Indicator to support custom tags ([626fe1a](https://github.com/liferay/liferay-portal/commit/626fe1acdc2106be3690f87c63820036b2a30511))
- LPD-79381 Add buttonProps and disabled support to MultiStepNav Indicator ([be6b4d5](https://github.com/liferay/liferay-portal/commit/be6b4d576a807c53d0dd85714b0ddbe4ce92cb86))
- LPD-79381 Add disabled props to the step Item ([cc280ca](https://github.com/liferay/liferay-portal/commit/cc280ca56bd641c07359db4be0e904cd4eff9e0b))
- LPD-79381 Add Disabled and NonInteractive stories to MultiStepNav ([d5db4bc](https://github.com/liferay/liferay-portal/commit/d5db4bc68041990beea9d0eac0a6633af135bb72))
- LPD-79381 Remove deprecated complete prop from MultiStepNav stories ([cbfed5f](https://github.com/liferay/liferay-portal/commit/cbfed5fd56bd3c52a688fd66fd4cf09c502ea1a7))
- LPD-79381 Respect 'as' prop and replace buttonProps with elementProps ([ffe7a0a](https://github.com/liferay/liferay-portal/commit/ffe7a0afabedbab5b236483bab2c30a0d5b2c58e))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))

## @clayui/nav

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-78996 @clayui/nav VerticalNav should allow stacked navs with no indent ([d824c08](https://github.com/liferay/liferay-portal/commit/d824c08a994b3babfd22902dcb739a9f0ed11e26))
- LPD-82605 Remove skip from tests, they pass ([228e368](https://github.com/liferay/liferay-portal/commit/228e3681671e3f3907f714b2f96b96dc15b9c0fd))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))

## @clayui/navigation-bar

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))

## @clayui/pagination

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-83387 Add global library ([b9fd33b](https://github.com/liferay/liferay-portal/commit/b9fd33b2f37a3f52c130ece8e208d1f7e0941b56))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))

## @clayui/pagination-bar

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))

## @clayui/panel

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))

## @clayui/popover

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-85584 Fix focus not returning to trigger after popover blur | When navigating via keyboard, focus moves to the popover when it opens. If a blur event occurs, focus is lost instead of returning to the previous element. This fix ensures that focus returns to the trigger element. ([29d35b0](https://github.com/liferay/liferay-portal/commit/29d35b01cf9bd77033f454e45d041493b1202b47))

## @clayui/progress-bar

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-79357 Add classname to progress bar ([cab0a14](https://github.com/liferay/liferay-portal/commit/cab0a14942e3ec5c95ece4f8671e51839297f65e))
- LPD-79357 @clayui/progress-bar adds fillBarClassName example in Storybook ([5130f70](https://github.com/liferay/liferay-portal/commit/5130f706f8c16440be3edece982c6f4f53e10455))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))

## @clayui/provider

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-82604 Fix DataClient, which didn't provide a method to clear completed promises, breaking the CacheAndNetwork fetch policy ([b99f481](https://github.com/liferay/liferay-portal/commit/b99f481a35f2da518491229e6a78607bd6920213))
- LPD-66973 Retrigger focus ring animation on browser tab return ([886d229](https://github.com/liferay/liferay-portal/commit/886d2292438211b68c328c729542e23d1319bb67))
- LPD-66973 Avoid animation conflict and improve tab returning effect ([9f24b66](https://github.com/liferay/liferay-portal/commit/9f24b66a91eef63489c5ab44fd762ab3735f8bd1))
- LPD-66973 Use constants to avoid CSS class repetition ([565d0a2](https://github.com/liferay/liferay-portal/commit/565d0a26c78727edc74d538262fc66f8b07ebbc6))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))

## @clayui/shared

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-82606 Use fireEvent. userEvent was triggering keyups between ArrowRight events so the last part about acceleration was never working ([0c313b9](https://github.com/liferay/liferay-portal/commit/0c313b9533b46e30ab078777b03b5e45dc9b4b6b))
- LPD-84613 Move the usePanelWidthMax hook to clay-shared and rename it to useObservedMaxWidth ([6f6c2a7](https://github.com/liferay/liferay-portal/commit/6f6c2a771008635ed912dda53edf72240fdae6aa))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))
- LPD-69776 Rename useOverlayPositon to useOverlayPosition ([7152aae](https://github.com/liferay/liferay-portal/commit/7152aae19e8be4380d785f0c2bd7610923e3306c))
- LPD-69776 Reset sourceElement positioning ([435b602](https://github.com/liferay/liferay-portal/commit/435b602a0f282ec1b77267edd4eff65827d48214))
- LPD-69776 Add test coverage to doAlign positioning reset ([b137c44](https://github.com/liferay/liferay-portal/commit/b137c44f0976324e171159adcbad5aa7abe08851))
- LPD-88290 Move PanelResizer to clay-core ([ddde603](https://github.com/liferay/liferay-portal/commit/ddde603ae4e42d51ceb23cbbd8a117f42e0662cc))
- LPD-88290 Rename PanelResizer by ResizeHandle ([d2453ea](https://github.com/liferay/liferay-portal/commit/d2453ea7bf7321cdcf04b9681cca1ca2447074d0))

## @clayui/slider

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))

## @clayui/sticker

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-78752 Add product icon ([5f5fb3f](https://github.com/liferay/liferay-portal/commit/5f5fb3fc32d41276098bfd249fd0080f46ed062c))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))

## @clayui/table

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))

## @clayui/tabs

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-83389 Remove tests related to modern and displayType props for ClayTabs since these attributes are not supported anymore and not used by the List component since Apr 10 2023 ([0cfc6a7](https://github.com/liferay/liferay-portal/commit/0cfc6a77f01c76e7e72fd42cc132bb23ea0c5c55))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))

## @clayui/time-picker

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))

## @clayui/toolbar

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))

## @clayui/tooltip

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))

## @clayui/upper-toolbar

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-82700 Conditionally add aria-controls attribute to ClayDropDown trigger, so it won't reference a element that isn't present in the DOM ([0b476ba](https://github.com/liferay/liferay-portal/commit/0b476bac00fa0482cc266f2f7ff4628213333350))
- LPD-86387 Update Clay to 3.161.0 ([191afb8](https://github.com/liferay/liferay-portal/commit/191afb830f09922e39bb9344a87ba276462f9a09))

# [3.161.0] (2026-04-16)

## @clayui/alert

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-81627 Allow passing classNames to the container ([1f9f4da](https://github.com/liferay/liferay-portal/commit/1f9f4da502edcfdd557a19645e383624cf9bb1fc))

## @clayui/autocomplete

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-82600 Fix Escape key input ([e02bcac](https://github.com/liferay/liferay-portal/commit/e02bcac12cbca7dd8d379f2b6a1ab81928aab56b))

## @clayui/badge

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-83029 Allow passing icon to badge ([26ceeb3](https://github.com/liferay/liferay-portal/commit/26ceeb3731afc917d0621aea45d967f16d2df0e5))
- LPD-83029 Add test ([ffa79b5](https://github.com/liferay/liferay-portal/commit/ffa79b5ed6336425bf6a5e9e06574a78ece42b58))
- LPD-83029 Implement ItemAfter, ItemBefore and ItemExpand logic ([19fcf1f](https://github.com/liferay/liferay-portal/commit/19fcf1f41c0cbb34cefb00bcd51f9cfcce7722b1))

## @clayui/breadcrumb

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))

## @clayui/button

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))

## @clayui/card

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-82700 Conditionally add aria-controls attribute to ClayDropDown trigger, so it won't reference a element that isn't present in the DOM ([0b476ba](https://github.com/liferay/liferay-portal/commit/0b476bac00fa0482cc266f2f7ff4628213333350))

## @clayui/color-picker

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-82601 Remove skip, test is working fine ([e0ad6fe](https://github.com/liferay/liferay-portal/commit/e0ad6fee201ce87c88db7b00453a265ee9c35785))
- LPD-80236 Convert index.ts into barrel file for exports ([ca8603a](https://github.com/liferay/liferay-portal/commit/ca8603a4049edfe93d16ad4b4dfa618362e35030))
- LPD-80236 Add useColorPicker hook ([82a441e](https://github.com/liferay/liferay-portal/commit/82a441e1e9a3969ae9e09d19e46607e304707916))
- LPD-80236 Add Field component to isolate the color input, splotch and dropdown ([74de4f2](https://github.com/liferay/liferay-portal/commit/74de4f237c92208742c97a8d25aae7157cb40375))
- LPD-80236 Export useColorPicker hook, Field and Editor components to reuse them ([2b55a42](https://github.com/liferay/liferay-portal/commit/2b55a42394e109edf4e454a4ea8e1cffd01cbe39))
- LPD-80236 Add the ability to add an external trigger reference ([13892d2](https://github.com/liferay/liferay-portal/commit/13892d25edd143ec7204c5c96b6870058ea9993a))
- LPD-80236 Adapt the parseColorValue function to the ClayColorPicker, taking into account the hex6 and hex8 ([c9d85e9](https://github.com/liferay/liferay-portal/commit/c9d85e96be51906c6c8699d95936255486f17418))
- LPD-80236 Save the value when onBlur is done in the Editor hex input ([ab85832](https://github.com/liferay/liferay-portal/commit/ab85832fee860e17e1a6df45b397987055a23059))
- LPD-80236 Improve props and descriptions ([6da7abb](https://github.com/liferay/liferay-portal/commit/6da7abb3a3dc9f6afcbf4eb24febbe592d4af6d9))
- LPD-80236 Rename onClickSplotch to onSplotchClick ([bec76ac](https://github.com/liferay/liferay-portal/commit/bec76acc04c3bbd9ed357adf790094fde7f09e29))
- LPD-80236 Create new function to format the hex colors ([bed726a](https://github.com/liferay/liferay-portal/commit/bed726a2fb3ce44c76a4157ac4adb2c93a77de40))
- LPD-83988 Export all the necessary functions from util.ts ([fdd7b2c](https://github.com/liferay/liferay-portal/commit/fdd7b2c82fcd839f34d5f5f7f29bd27b827c74c4))
- LPD-83988 Update the package types in the color-picker docs ([d2b0084](https://github.com/liferay/liferay-portal/commit/d2b00844c744b7175051336914471d4dd3d19957))

## @clayui/core

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-78752 Allow configuring custom classes in Clay SidePanel.Title ([5626239](https://github.com/liferay/liferay-portal/commit/56262393602378d933b9d7962fc609700b4e8407))
- LPD-79027 Use existing onEnd callback ([d6d039d](https://github.com/liferay/liferay-portal/commit/d6d039d7073cbc6558ca4ab14bc08eb372c035bb))
- LPD-79027 Reset state also after drop with mouse ([03de461](https://github.com/liferay/liferay-portal/commit/03de46132d6ac765df42adc6452da3ab3280a493))
- LPD-78996 @clayui/nav VerticalNav should allow stacked navs with no indent ([d824c08](https://github.com/liferay/liferay-portal/commit/d824c08a994b3babfd22902dcb739a9f0ed11e26))
- LPD-78996 @clayui/core VerticalNav add example without indent ([60adeec](https://github.com/liferay/liferay-portal/commit/60adeec5ac228a6ae1e7a39042c3770a6c0c9a9d))
- LPD-79543 Allow side panel to disable the escape key behavior ([1aa3734](https://github.com/liferay/liferay-portal/commit/1aa3734ee319c84899b114eef93587bdf0fd9658))
- LPD-66630 Update size to small ([096b025](https://github.com/liferay/liferay-portal/commit/096b02530c74447b42f14aa7383aeef733559b6f))
- LPD-66630 Update snapshots ([4f5e77b](https://github.com/liferay/liferay-portal/commit/4f5e77b42757bc9a0d4df11a6a6826156bfaa976))
- LPD-79219 @clayui/core Treeview drag handler should be 24px x 24px when focused ([2cc084a](https://github.com/liferay/liferay-portal/commit/2cc084ae2a760b05df962eff3aff1e9b920383ba))
- LPD-75776 Fix typo ([9d38ea9](https://github.com/liferay/liferay-portal/commit/9d38ea9f9a3745e95d519a66ece726ad13fcf40d))
- LPD-81627 Make symbol optional ([2343e95](https://github.com/liferay/liferay-portal/commit/2343e95f6dc7d6d4f0d3fd6b2d4b1220b2002291))
- LPD-82602 Fix keyboard events ([cde7716](https://github.com/liferay/liferay-portal/commit/cde7716b174daa483aa7724e28846ccc94eb246c))
- LPD-82700 Conditionally add aria-controls attribute to ClayDropDown trigger, so it won't reference a element that isn't present in the DOM ([0b476ba](https://github.com/liferay/liferay-portal/commit/0b476bac00fa0482cc266f2f7ff4628213333350))
- LPD-82603 Fix escape key input in skipped side panel test ([e0029ee](https://github.com/liferay/liferay-portal/commit/e0029eec3a6084e0a952a4f4e5f7c1fb66e59e21))
- LPD-80239 Add FocusTrap to the dropdown menu ([0a906b5](https://github.com/liferay/liferay-portal/commit/0a906b542b5475edf2e05f4ed04eaf61dd23967b))
- LPD-85711 Add opt-in search capability to the Picker component ([f14c1f9](https://github.com/liferay/liferay-portal/commit/f14c1f94459d7a0876c7a5dfa51bf8df6b328daf))
- LPD-85711 Add test coverage to Picker search capability ([8201b1e](https://github.com/liferay/liferay-portal/commit/8201b1e67ea5acfd5ce9d292a022ec518b041d79))
- LPD-85711 Update Picker documentation to include the new search capability ([d465000](https://github.com/liferay/liferay-portal/commit/d4650005e444fb892fd2af4dfa1bab4ae9634e73))
- LPD-85711 Refactor Picker component to modularize search logic ([4c7178e](https://github.com/liferay/liferay-portal/commit/4c7178e991a4abe6dd5b843f3f80f23bf6ec8ef3))
- LPD-85711 Improve search accessibility ([a8a06e0](https://github.com/liferay/liferay-portal/commit/a8a06e0f6a5a61196a6dec4ead16716f5d423720))
- LPD-85711 Reorganize props ([1fca9cd](https://github.com/liferay/liferay-portal/commit/1fca9cd03e891910f79f92a26491d3f2ce42c242))

## @clayui/css

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-75997 @clayui/css Update product-menu-open.svg ([1db5459](https://github.com/liferay/liferay-portal/commit/1db545935c65cc6ca37ae37b57f27951d42405f6))
- LPD-79073 Control Menu button icons should be 16px ([36a0f8a](https://github.com/liferay/liferay-portal/commit/36a0f8adaba142c08d1ac8c460ed919ee81ff9c9))
- LPD-77928 @clayui/css SF variable files ([1fe4e68](https://github.com/liferay/liferay-portal/commit/1fe4e6817515190e3f41d435bc62f3436ba59c0d))
- LPD-79018 @clayui/css sticker-xxl icon should be 40px ([2fd0409](https://github.com/liferay/liferay-portal/commit/2fd0409656bdda0f9e2f765891de2a45660e4237))
- LPD-80362 @clayui/core Treeview icon angle-down should display larger ([22eb0b3](https://github.com/liferay/liferay-portal/commit/22eb0b3c7b87daa3ee9d61538dad07303d844537))
- LPD-78750 @clayui/css adds rocket.svg ([340f130](https://github.com/liferay/liferay-portal/commit/340f13097d6f9f3884532d483edaf83b5c6dd3f3))
- LPD-79219 @clayui/core Treeview drag handler should be 24px x 24px when focused ([2cc084a](https://github.com/liferay/liferay-portal/commit/2cc084ae2a760b05df962eff3aff1e9b920383ba))
- LPD-80374 @clayui/css add icons calculator, price-list, product-configuration, and products ([cffbeff](https://github.com/liferay/liferay-portal/commit/cffbeff59e3509c2b132969f49d4d5ce1898c232))
- LPD-80374 SF icons.svg ([c27b09c](https://github.com/liferay/liferay-portal/commit/c27b09c03777ee017fe66d7ebd5a93e66ff4e3fc))
- LPD-77810 @clayui/css Replace Sass math with CSS calc ([75730ce](https://github.com/liferay/liferay-portal/commit/75730ce27d3621b0c73c5256be4568891d44eec8))
- LPD-77810 @clayui/css Replace math-sign with CSS calc ([42a8656](https://github.com/liferay/liferay-portal/commit/42a865608c05a36c2d2fd5b313b733a8d0709726))
- LPD-77810 @clayui/css SF ([4eb1545](https://github.com/liferay/liferay-portal/commit/4eb154584aeb8bdbd82038223b5a719b81769282))
- LPD-80439 @clayui/css add layout-new-window.svg ([2310a7f](https://github.com/liferay/liferay-portal/commit/2310a7f5800515f39300f77362ccf8415d202c0f))
- LPD-79381 Scope cursor and hover effects to interactive elements ([8ae76f2](https://github.com/liferay/liferay-portal/commit/8ae76f2912116397bdad8bade5e7c8e6faa7c26c))
- LPD-81799 - Regen ([e577af8](https://github.com/liferay/liferay-portal/commit/e577af8942abc4abfc9811ba91781239cf08ef6d))
- LPD-81799 - SF ([3a6f4e5](https://github.com/liferay/liferay-portal/commit/3a6f4e5c95e8e68de19e2902eb8b673311a241c2))
- LPD-80549 @clayui/css clay-css mixin should support any CSS property ([49fc8b8](https://github.com/liferay/liferay-portal/commit/49fc8b8ad1a059fad8facc1f5e164c1373466270))
- LPD-81799 clayui.com should use local version of icons.svg in dev mode ([d57b27c](https://github.com/liferay/liferay-portal/commit/d57b27cec0cf0098114a233eced849776d0d9cd5))
- LPD-81820 @clayui/css add unit to 0 values to make it work with calc() ([9a5ffbf](https://github.com/liferay/liferay-portal/commit/9a5ffbffbb75908bc23dae70b177bcf434507fab))
- LPD-82273 @clayui/css remove use of Sass nth function with variables that could contain a CSS variable ([503c60e](https://github.com/liferay/liferay-portal/commit/503c60e225c5a2b223c7476fc007325d86172433))
- LPD-77641 @clayui/css $custom-control-indicator-checked-color should propagate to checkbox and radio ([02fe938](https://github.com/liferay/liferay-portal/commit/02fe938e328af2c1a472d06ae8ab20d55d018242))
- LPD-83068 @clayui/css add books-brush.svg ([3d3f1b2](https://github.com/liferay/liferay-portal/commit/3d3f1b221cf72464d7f28ec9ea0c907c8775cf31))
- LPD-71471 @clayui/css clay-data-label-text-position function shouldn't error with CSS variables ([a88e120](https://github.com/liferay/liferay-portal/commit/a88e1203878e72258f746b31633cfd03b3666090))
- LPD-71471 @clayui/css Use calc for $spacers ([3a8d947](https://github.com/liferay/liferay-portal/commit/3a8d9476d2c67482bc535ebf16aad92fa46f9f7a))
- LPD-71471 @clayui/css Add Atlas custom properties theme ([868a5cf](https://github.com/liferay/liferay-portal/commit/868a5cf310d35d0ea06ade8e0882057710af3028))
- LPD-81854 @clayui/css atlas variable import order should match base variable import order ([1c52c57](https://github.com/liferay/liferay-portal/commit/1c52c57b0f9427edea79b6116ec9dc2d0a2a87df))

## @clayui/data-provider

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-82604 Fix clay-data-provider test with CacheFirst, it was expecting the cache to be already populated ([38fb06d](https://github.com/liferay/liferay-portal/commit/38fb06d2913f0b985ae53010ff60de76601ad0c8))
- LPD-82604 Fix DataClient, which didn't provide a method to clear completed promises, breaking the CacheAndNetwork fetch policy ([b99f481](https://github.com/liferay/liferay-portal/commit/b99f481a35f2da518491229e6a78607bd6920213))
- LPD-82604 Fix clay-data-provider test with CacheAndNetwork, it was expecting the cache to be already populated ([337d076](https://github.com/liferay/liferay-portal/commit/337d07695378423d84cd4aace237df41cbf49cca))
- LPD-82604 Fix clay-data-provider test using Node.js-specific unref method, adapting it to reflect the browser environment ([a540d95](https://github.com/liferay/liferay-portal/commit/a540d95ebaffac1d8b31b5d53741ab2e88a8add4))

## @clayui/date-picker

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))

## @clayui/drop-down

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-82700 Conditionally add aria-controls attribute to ClayDropDown trigger, so it won't reference a element that isn't present in the DOM ([0b476ba](https://github.com/liferay/liferay-portal/commit/0b476bac00fa0482cc266f2f7ff4628213333350))
- LPD-82700 Remove aria-labelledby for DropDown groups without header ([dd8f061](https://github.com/liferay/liferay-portal/commit/dd8f061b49d3a731d14a2e101e6abed533ff135b))
- LPD-83370 Enable DropDownWithDrilldown skipped tests ([aefacb2](https://github.com/liferay/liferay-portal/commit/aefacb276312ec759f3554e41b37f04932b1f067))

## @clayui/empty-state

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))

## @clayui/form

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-80237 Allow passing refs to the Clay Select ([55e2649](https://github.com/liferay/liferay-portal/commit/55e2649bdc0aef25991539f1d4fa9c4e4a212645))
- LPD-83371 Unskip test. Use the callback function to test the multiple options ([c13571d](https://github.com/liferay/liferay-portal/commit/c13571d350579998edfa8aa6228f778f8d81e74a))

## @clayui/icon

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-78750 @clayui.com add rocket.svg to docs ([e74d242](https://github.com/liferay/liferay-portal/commit/e74d2429a8f8c1b6fc1e328bd68ff82322f8fc22))
- LPD-80374 clayui.com add aliases for calculator, price-list, product-configuration and products icons ([c47704a](https://github.com/liferay/liferay-portal/commit/c47704a0a6032017996175da941ab6062fed9ff9))
- LPD-80439 clayui.com document layout-new-window.svg ([7422fbf](https://github.com/liferay/liferay-portal/commit/7422fbfd99740a28d6c088cddec5dcaf3fa77652))
- LPD-83068 clayui.com add books-brush.svg and aliases ([5e950e1](https://github.com/liferay/liferay-portal/commit/5e950e19fe9fa530ee36a2813a6d4ce8f06b31ef))

## @clayui/label

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))

## @clayui/layout

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))

## @clayui/link

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))

## @clayui/list

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))

## @clayui/loading-indicator

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))

## @clayui/localized-input

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))

## @clayui/management-toolbar

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))

## @clayui/modal

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-79663 Only render items if elements are provided ([20f3b19](https://github.com/liferay/liferay-portal/commit/20f3b193ef3a1cf1d5ee43a8a7bcaaee4bcd2dd2))
- LPD-79663 Add test ([d5d832d](https://github.com/liferay/liferay-portal/commit/d5d832d6c11e94cacbb78973c93443fe8e10906d))
- LPD-79663 Update snapshots ([b4906d5](https://github.com/liferay/liferay-portal/commit/b4906d5be205e68db99cb415e6da2ff850ea59d8))

## @clayui/multi-select

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-83372 Enable skipped clay-multi-select tests ([3764509](https://github.com/liferay/liferay-portal/commit/3764509a95729468bc1054782d6a9a406b25b665))

## @clayui/multi-step-nav

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-79381 Add as prop to Indicator to support custom tags ([626fe1a](https://github.com/liferay/liferay-portal/commit/626fe1acdc2106be3690f87c63820036b2a30511))
- LPD-79381 Add buttonProps and disabled support to MultiStepNav Indicator ([be6b4d5](https://github.com/liferay/liferay-portal/commit/be6b4d576a807c53d0dd85714b0ddbe4ce92cb86))
- LPD-79381 Add disabled props to the step Item ([cc280ca](https://github.com/liferay/liferay-portal/commit/cc280ca56bd641c07359db4be0e904cd4eff9e0b))
- LPD-79381 Add Disabled and NonInteractive stories to MultiStepNav ([d5db4bc](https://github.com/liferay/liferay-portal/commit/d5db4bc68041990beea9d0eac0a6633af135bb72))
- LPD-79381 Remove deprecated complete prop from MultiStepNav stories ([cbfed5f](https://github.com/liferay/liferay-portal/commit/cbfed5fd56bd3c52a688fd66fd4cf09c502ea1a7))
- LPD-79381 Respect 'as' prop and replace buttonProps with elementProps ([ffe7a0a](https://github.com/liferay/liferay-portal/commit/ffe7a0afabedbab5b236483bab2c30a0d5b2c58e))

## @clayui/nav

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-78996 @clayui/nav VerticalNav should allow stacked navs with no indent ([d824c08](https://github.com/liferay/liferay-portal/commit/d824c08a994b3babfd22902dcb739a9f0ed11e26))
- LPD-82605 Remove skip from tests, they pass ([228e368](https://github.com/liferay/liferay-portal/commit/228e3681671e3f3907f714b2f96b96dc15b9c0fd))

## @clayui/navigation-bar

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))

## @clayui/pagination

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-83387 Add global library ([b9fd33b](https://github.com/liferay/liferay-portal/commit/b9fd33b2f37a3f52c130ece8e208d1f7e0941b56))

## @clayui/pagination-bar

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))

## @clayui/panel

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))

## @clayui/popover

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))

## @clayui/progress-bar

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-79357 Add classname to progress bar ([cab0a14](https://github.com/liferay/liferay-portal/commit/cab0a14942e3ec5c95ece4f8671e51839297f65e))
- LPD-79357 @clayui/progress-bar adds fillBarClassName example in Storybook ([5130f70](https://github.com/liferay/liferay-portal/commit/5130f706f8c16440be3edece982c6f4f53e10455))

## @clayui/provider

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-82604 Fix DataClient, which didn't provide a method to clear completed promises, breaking the CacheAndNetwork fetch policy ([b99f481](https://github.com/liferay/liferay-portal/commit/b99f481a35f2da518491229e6a78607bd6920213))

## @clayui/shared

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-82606 Use fireEvent. userEvent was triggering keyups between ArrowRight events so the last part about acceleration was never working ([0c313b9](https://github.com/liferay/liferay-portal/commit/0c313b9533b46e30ab078777b03b5e45dc9b4b6b))

## @clayui/slider

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))

## @clayui/sticker

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-78752 Add product icon ([5f5fb3f](https://github.com/liferay/liferay-portal/commit/5f5fb3fc32d41276098bfd249fd0080f46ed062c))

## @clayui/table

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))

## @clayui/tabs

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-83389 Remove tests related to modern and displayType props for ClayTabs since these attributes are not supported anymore and not used by the List component since Apr 10 2023 ([0cfc6a7](https://github.com/liferay/liferay-portal/commit/0cfc6a77f01c76e7e72fd42cc132bb23ea0c5c55))

## @clayui/time-picker

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))

## @clayui/toolbar

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))

## @clayui/tooltip

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))

## @clayui/upper-toolbar

### Commits

- LPD-78372 - Run SF ([763733b](https://github.com/liferay/liferay-portal/commit/763733b6e5142030b6cd233c6cff7562d0763bf5))
- LPD-82700 Conditionally add aria-controls attribute to ClayDropDown trigger, so it won't reference a element that isn't present in the DOM ([0b476ba](https://github.com/liferay/liferay-portal/commit/0b476bac00fa0482cc266f2f7ff4628213333350))