The native Sass library requires a specific environment in order to build.
Once on the correct environment, run the related Gradle build task for creating
the library (e.g. "gradle buildLibSass_Linux_x86_64" for building the Linux
64-bit library).

We've renamed the library to liferaysass because jna will sometimes look into
the global lib location.  This can cause you to use whatever version of libsass
you have installed instead of the bundled version and cause issues since jna is
compiled against a specific version of libsass.

For resources/darwin/libliferaysass.dylib:
	- Install OSX 10.7.5.
	- Login to developer.apple.com.
	- Download "Command Line Tools (OS X Mountain Lion) for Xcode - April 2013".
	- Install package xcode462_cltools_10_76938260a.dmg.
	- Execute "gradle buildLibSass_Darwin" from sass-compiler.

For resources/linux-x86/libliferaysass.so:
	- Install Ubuntu 10.04 LTS 32-bit from http://old-releases.ubuntu.com/releases/10.04.3/ubuntu-10.04.4-desktop-i386.iso.
	- Install gcc 4.6.
	- Install g++ 4.6.
	- Execute "gradle buildLibSass_Linux_x86" from sass-compiler.

For resources/linux-x86-64/libliferaysass.so:
	- Install ubuntu 10.04 LTS 64-bit from http://old-releases.ubuntu.com/releases/10.04.3/ubuntu-10.04.4-desktop-amd64.iso.
	- Install gcc 4.6.
	- Install g++ 4.6.
	- Execute "gradle buildLibSass_Linux_x86_64" from sass-compiler.

For resources/win32-x86/lfieraysass.dll:
	- Install Windows 7 32-bit.
	- Download tdm-gcc 32-bit from http://sourceforge.net/projects/tdm-gcc/files/TDM-GCC%20Installer/tdm-gcc-5.1.0-3.exe/download.
	- Run the tdm-gcc 32-bit installer and then click "Create".
	- Install with all the defaults.
	- Execute "gradle buildLibSass_Win32_x86" from sass-compiler.

For resources/win32-x86-64/liferaysass.dll:
	- Install Windows 7 64-bit.
	- Download tdm64-gcc 64-bit from http://sourceforge.net/projects/tdm-gcc/files/TDM-GCC%20Installer/tdm64-gcc-5.1.0-2.exe/download.
	- Run the tdm64-gcc 64-bit installer and then click "Create".
	- Select MinGW-w64/TDM64 option
	- Install with all the defaults.
	- Execute "gradle buildLibSass_Win32_x86_64" from sass-compiler.