@spira_Segments @Segments @List @Deletion @team_FARO @priority_4
Feature: Delete a Segment via the Segment List
	As an Business User, I should be able to delete a Segment via the Segment List

	Background: [Setup] Create a Static Segment
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Static Segment" dropdown option
		* I click the "Add Members" button
		* I search for "Abram Bauch"
		* I click the checkbox on the table row containing "Abram Bauch"
		* I click the "Add" button
		* I name the Static segment "SegmentListDeleteSegment Segment - ${Random.1}" and save it

	Scenario: Delete a Segment via the Segment List - Inline Button
		Given I go to the "Segments" page
		When I click the inlined "Delete" button for the "SegmentListDeleteSegment Segment - ${Random.1}" row
		And I click the "Delete" button
		Then I should not see a Segment named "SegmentListDeleteSegment Segment - ${Random.1}" in the table

	Scenario: Delete a Segment via the Segment List - Kebab Menu Option
		Given I go to the "Segments" page
		When I click the inlined "Kebab" button for the "SegmentListDeleteSegment Segment - ${Random.1}" row
		And I click the "Delete" button
		And I click the "Delete" button
		Then I should not see a Segment named "SegmentListDeleteSegment Segment - ${Random.1}" in the table