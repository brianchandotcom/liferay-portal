@spira_Order @Order @Segments @List @Profile @team_FARO @priority_3
Feature: Order a Segment Profile's Interest
	As an Business User, I should be able to order a Segment Profile's Interests

	Background: [Setup] Navigate to a Segment Profile
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click "engineers" in the table

	Scenario: Order a Segment Profile's Interests
		Given I click the "Interests" tab

		# Order by Topic

		When I sort the table by the "Topic" column header in descending order
		Then I should see the following ordered rows in the bar graph table:
			| web-enabled interfaces   |
			| synergistic schemas      |
			| rich e-commerce          |
			| mesh synergistic schemas |
			| mesh                     |
			| cutting-edge platforms   |
		When I sort the table by the "Topic" column header in ascending order
		Then I should see the following ordered rows in the bar graph table:
			| cutting-edge platforms   |
			| mesh                     |
			| mesh synergistic schemas |
			| rich e-commerce          |
			| synergistic schemas      |
			| web-enabled interfaces   |

		# Order by Segment Members

		When I sort the table by the "Segment Members" column header in descending order
		Then I should see the following ordered rows in the bar graph table:
			| cutting-edge platforms   |
			| mesh                     |
			| mesh synergistic schemas |
			| synergistic schemas      |
			| rich e-commerce          |
			| web-enabled interfaces   |
		When I sort the table by the "Segment Members" column header in ascending order
		Then I should see the following ordered rows in the bar graph table:
			| rich e-commerce          |
			| web-enabled interfaces   |
			| cutting-edge platforms   |
			| mesh                     |
			| mesh synergistic schemas |
			| synergistic schemas      |
