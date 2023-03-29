@component_Segments @team_FARO @priority_3
Feature: Assert no interest found in segment
	As a Business User, I should be able to view no interest found message

	Background: [Setup] Create a segment with no interests
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Static Segment" dropdown option
		* I click the "Add Members" button
		* I click the checkbox on the table row containing "Annis Osinski"
		* I click the "Add" button
		* I name the Static segment "NoInterestStaticSegment - ${Random.1}" and save it

	Scenario: Assert no interest found
		Given I go to the "Segments" page
		And I click "NoInterestStaticSegment - ${Random.1}" in the table
		When I click the "Interests" tab
		Then I should see text saying "There are no Interests found." on the page