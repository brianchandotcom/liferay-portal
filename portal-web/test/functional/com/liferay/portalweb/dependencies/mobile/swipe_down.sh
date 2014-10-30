# !/bin/sh

sendevent /dev/input/event0 3 0 7
sendevent /dev/input/event0 3 1 250
sendevent /dev/input/event0 1 330 1
sendevent /dev/input/event0 0 0 0
sleep 0.5
sendevent /dev/input/event0 3 1 650
sendevent /dev/input/event0 0 0 0
sendevent /dev/input/event0 1 330 0
sendevent /dev/input/event0 0 0 0