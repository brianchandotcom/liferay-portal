@component_Search @component_Individuals @team_FARO @priority_3
Feature: Assert the No Results Found message on an Individual's Overview Associated Segment
	As a Business User, I should be able to search associated segment and see "No Results Found" message

	Background: [Setup] Navigate to Individuals page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Individuals" page

	Scenario: Search associated segment will not return results
		Given I click the "Known Individuals" tab
		And I click "Abram Bauch" in the table
		When I search for "No Results" in the Associated Segments card
		Then I should see a message that there are no "Segments" found