@spira_Order @Order @Segments @List @team_FARO @priority_3
Feature: Order the Segment Profile Distribution
	As a Business User, I should be able to order a Segment Profile Distribution

	Background: [Setup] Navigate to Individuals Segment Creation Page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click "everybody" in the table

	Scenario: Order the Segment Profile Distribution
		Given I click the "Distribution" tab
		And I select breakdown "Individuals" by "gender"
		And I click row number "1" in the bar graph table
		When I sort the table by the "Name" column header in descending order
		Then I should see the users sorted in descending alphabetical order
		When I sort the table by the "Name" column header in ascending order
		Then I should see the users sorted in ascending alphabetical order