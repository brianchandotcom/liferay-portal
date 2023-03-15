@spira_Segments @Segments @team_FARO @priority_3
Feature: Segment distribution chart can be filtered by text property
	As a Business User, I should be able to filter distribution by text property

	Background: [Setup] Navigate to Segments
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page

	Scenario: Chart filtered by text property
		Given I click "everybody" in the table
		And I click the "Distribution" tab
		When I select breakdown "Individuals" by "fullName"
		And I click row number "1" in the bar graph table
		Then I should see an individual named "Abram Bauch" in the table